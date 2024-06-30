## What is Distributed Cache?

Distributed cache is a caching technique where cache is spread across multiple machines across multiple nodes spread across clusters and sometimes across data centers located around the globe.

## Distributed cache is primarily used for

#### 1. High Availability

As the name signifies, distributing the cache across multiple machines help us to improve the cache availability. If one of the instance goes down for some reason, still we might have support from other machines which shared the load. We can also created a back up for every instance so that always specific number of instances will be maintained with reserved passive instances.


#### 2. Scalability

It will be easily scalable as the data will be stored in multiple locations which makes cache to remain light weight and smaller in size which in-turn help to perform search operation at a good pace.
Caching Patterns/Policies:


## Here are some of the commonly used caching patterns.

#### 1. Cache-Aside
#### 2. Read-Through
#### 3. Write-Through
#### 4. Write-Back/Behind
#### 5. Write-Around
#### 6. Refresh-Ahead


## Must Have features

### 1. Expirations

How long the cache will be stored.



### 2. Eviction

When the cache reaches this size, it should start removing cached items to make room for new ones, a process usually referred to as evictions. Popular algorithms are LPR and LFU.


### 3. Caching Relational Data

ASP.NET Cache has a really cool feature called CacheDependency, which allows you to keep track of relationships between different cached items. Some commercial caches also have this feature. Below is shown an example of how ASP.NET Cache works. 

Using System.Web.Caching; 

    public void CreateKeyDependency() { 

        Cache["key1"] = "Value 1"; // Make key2 dependent on key1. 

        String[] dependencyKey = new String[1]; dependencyKey[0] = "key1"; 

        CacheDependency dep1 = new CacheDependency(null, dependencyKey); 

        Cache.Insert("key2", "Value 2", dep2); 

    }


This is a multilayer dependency, meaning that A can depend on B and B can depend on C. So, if your application removes C, A and B have to be removed from the cache as well.

### 4. Synchronizing a Cache with Other Environments

You also need to synchronize your cache with data sources that you and your cache don’t have access to so that changes in those data sources are reflected in your cache to keep it fresh.

For example, let’s say your cache is written using the Microsoft .NET Framework, but you have Java or C++ applications modifying data in your master data source. You want these applications to notify your cache when certain data changes in the master data sources so that your cache can invalidate a corresponding cached item.

Ideally, your cache should support this capability. If it doesn’t, this burden falls on your application. ASP.NET Cache provides this feature through CacheDependency, as do some commercial caching solutions. It allows you to specify that a certain cached item depends on a file and that whenever this file is updated or removed, the cache discovers this and invalidates the cached item. Invalidating the item forces your application to fetch the latest copy of this object the next time your application needs it and does not find it in the cache.


### 5. Database Synchronization

If the cache has an old copy and the database a new copy, you now have a data integrity problem because you don’t know which copy is right. Of course, the database is always right, but you don’t always go to the database. You get data from the cache because your application trusts that the cache will always be correct or that the cache will be correct enough for its needs.

Synchronizing with the database can mean invalidating the related item in the cache so that the next time your application needs it, it will fetch it from the database. One interesting variant to this process is when the cache automatically reloads an updated copy of the object when the data changes in the database. However, this is possible only if your cache allows you to provide a read-through handler and then uses it to reload the cached item from the database. However, only some of the commercial caches support this feature, and none of the free ones do.

using System.Web.Caching; using System.Data.SqlClient;

    public void CreateSqlDependency(Customers cust, SqlConnection conn) { 
    
        // Make cust dependent on a corresponding row in the 
        
        // Customers table in Northwind database 
        
        string sql = "SELECT CustomerID FROM Customers WHERE "; 
        
        sql += "CustomerID = @ID"; 
        
        SqlCommand cmd = new SqlCommand(sql, conn); 
        
        cmd.Parameters.Add("@ID", System.Data.SqlDbType.VarChar); 
        
        cmd.Parameters["@ID"].Value = cust.CustomerID; 
        
        SqlCacheDependency dep = new SqlCacheDependency(cmd); 
        
        string key = "Customers:CustomerID:" + cust.CustomerID; 
        
        Cache.Insert(key, cust, dep); 
    
    }

ASP.NET Cache SqlCacheDependency allows you to specify a SQL string to match one or more rows in a table in the database. If this row is ever updated, the DBMS fires a .NET event that your cache catches. It then knows which cached item is related to this row in the database and invalidates that cached item.

One capability that ASP.NET Cache does not provide but that some commercial solutions do is polling-based database synchronization. This capability is helpful in two situations. First, if your DBMS does not have the .NET CLR built into it, you cannot benefit from SqlCacheDependency. In that case, it would be nice if your cache could poll your database on configurable intervals and detect changes in certain rows in a table. If those rows have changed, your cache invalidates their corresponding cached items.

The second situation is when data in your database is frequently changing and .NET events are becoming too chatty. This occurs because a separate .NET event is fired for each SqlCacheDependency change, and if you have thousands of rows that are updated frequently, this could easily crowd your cache. In such cases, it is much more efficient to rely on polling, where with one database query you can fetch hundreds or thousands of rows that have changed and then invalidate corresponding cached items. Of course, polling creates a slight delay in synchronization (maybe 15–30 seconds), but this is acceptable in many cases.


### 6. Read-Through

In a nutshell, read-through is a feature that allows your cache to directly read data from your data source, whatever that may be. You write a read-through handler and register it with your cache, and then your cache calls this handler at appropriate times.

    using System.Web.Caching; 
    
    using System.Data.SqlClient; 
    
    using Company.MyDistributedCache;
    
    public class SqlReadThruProvider : IReadhThruProvider { 
    
        private SqlConnection _connection; 
        
        // Called upon startup to initialize connection 
        
        public void Start(IDictionary parameters) { 
        
            _connection = new SqlConnection(parameters["connstring"]); 
            
            _connection.Open(); 
        
        } 
        
        // Called at the end to close connection 
        
        public void Stop() { 
        
            _connection.Close(); 
        
        } 
        
        // Responsible for loading object from external data source
        
        public object Load(string key, ref CacheDependency dep) { 
        
            string sql = "SELECT * FROM Customers WHERE "; 
            
            sql += "CustomerID = @ID"; 
            
            SqlCommand cmd = new SqlCommand(sql, _connection); 
            
            cmd.Parameters.Add("@ID", System.Data.SqlDbType.VarChar); 
            
            // Let's extract actual customerID from "key" 
            
            int keyFormatLen = "Customers:CustomerID:".Length; 
            
            string custId = key.Substring(keyFormatLen, key.Length - keyFormatLen); 
            
            cmd.Parameters["@ID"].Value = custId; 
            
            // fetch the row in the table 
            
            SqlDataReader reader = cmd.ExecuteReader(); 
            
            // copy data from "reader" to "cust" object 
            
            Customers cust = new Customers(); 

            FillCustomers(reader, cust); 
            
            // specify a SqlCacheDependency for this object 
            
            dep = new SqlCacheDependency(cmd); 
            
            return cust; 
        
        } 
    
    }

Because a distributed cache usually lives outside your application, it is shared across multiple instances of your application or even multiple applications. One important capability of a read-through handler is that the data you cache is fetched by the cache directly from the database. Hence, your applications don’t have to have database code. They can just fetch data from the cache, and if the cache doesn’t have it, the cache goes and takes it from the database.

You gain even more important benefits if you combine read-through capabilities with expirations. Whenever an item in the cache expires, the cache automatically reloads it by calling your read-through handler. You save a lot of traffic to the database with this mechanism. The cache uses only one thread, one database trip, to reload that data from the database, whereas you might have thousands of users trying to access that same data. If you did not have read-through capability, all those users would be going directly to the database, inundating the database with thousands of parallel requests.

However, keep in mind that read-through is not a substitute for performing some complex joined queries in the database. A typical cache does not let you do these types of queries. A read-through capability works well for individual object read operations but not in operations involving complex joined queries, which you always need to perform on the database.


### 7. Write-through and Write-Behind

Write-through is just like read-through: you provide a handler, and the cache calls the handler, which writes the data to the database whenever you update the cache. One major benefit is that your application doesn’t have to write directly to the database because the cache does it for you. This simplifies your application code because the cache, rather than your application, has the data access code.

Normally, your application issues an update to the cache (for example, Add, Insert, or Remove). The cache updates itself first and then issues an update call to the database through your write-through handler. Your application waits until both the cache and the database are updated.

What if you want to wait for the cache to be updated, but you don’t want to wait for the database to be updated because that slows down your application’s performance? That’s where write-behind comes in, which uses the same write-through handler but updates the cache synchronously and the database asynchronously. This means that your application waits for the cache to be updated, but you don’t wait for the database to be updated.

You know that the database update is queued up and that the database is updated fairly quickly by the cache. This is another way to improve your application performance. You have to write to the database anyway, but why wait? If the cache has the data, you don’t even suffer the consequences of other instances of your application not finding the data in the database because you just updated the cache, and the other instances of your application will find the data in the cache and won’t need to go to the database.

### 8. Cache Query


Normally, your application finds objects in the cache based on a key, just like a hash table, as you’ve seen in the source code examples above. You have the key, and the value is your object. But sometimes you need to search for objects based on attributes other than the key. Therefore, your cache needs to provide the capability for you to search or query the cache.

There are a couple of ways you can do this. One is to search on the attributes of the object. The other involves situations in which you’ve assigned arbitrary tags to cached objects and want to search based on the tags. Attribute-based searching is currently available only in some commercial solutions through object query languages, but tag-based searching is available in commercial caches and in Microsoft Velocity.

Let’s say you’ve saved a customer object. You could say, “Give me all the customers where the city is San Francisco,” when you want only customer objects, even though your cache has employees, customers, orders, order items, and more. When you issue a SQL-like query such as the one shown in Figure 5, it finds the objects that match your criteria.

    using Company.MyDistributedCache;
    
    public List<Customers> FindCustomersByCity(Cache cache, string city) { 
    
        // Search cache with a LINQ query 
        
        List<Customers> custs = from cust in cache.Customers where cust.City == city select cust; 
        
        return custs; 
    
    }

Tagging lets you attach multiple arbitrary tags to a specific object, and the same tag can be associated with multiple objects. Tags are usually string-based, and tagging also allows you to categorize objects into groups and then find the objects later through these tags or groups.


### 9. Event Propagation

You might not always need event propagation in your cache, but it is an important feature that you should know about. It’s a good feature to have if you have distributed applications, HPC applications, or multiple applications sharing data through a cache. What event propagation does is ask the cache to fire events when certain things happen in the cache. Your applications can capture these events and take appropriate actions in response.

Say your application has fetched some object from the cache and is displaying it to the user. You might be interested to know if anybody updates or removes this object from the cache while it is displayed. In this case, your application will be notified, and you can update the user interface.

This is, of course, is a very simple example. In other cases, you might have a distributed application where some instances of your application are producing data and other instances need to consume it. The producers can inform the consumers when data is ready by firing an event through the cache that the consumers receive. There are many examples of this type, where collaboration or data sharing through the cache can be achieved through event propagation.


### 10. High Availability

Because a distributed cache runs as a server in your production environment, and in many cases serves as the only data store for your application (for example, ASP.NET session state), the cache must provide high availability. This means that your cache must be very stable so that it never crashes and provides the ability to make configuration changes without stopping the cache.

Most users of a distributed cache require the cache to run without any interruptions for months at a time. Whenever they have to stop the cache, it is usually during a scheduled down time. That is why high availability is so critical for a distributed cache. Here are a few questions to keep in mind when evaluating whether a caching solution provides high availability.

Can you bring one of the cache servers down without stopping the entire cache?

Can you add a new cache server without stopping the cache?

Can you add new clients without stopping the cache?

In most caches, you use a specified maximum cache size so that the cache doesn’t exceed the amount of data. The cache size is based on how much memory you have available on that system. 

Can you change that capacity? 

Let’s say you initially set the cache size to be 1GB but now want to make it 2GB. Can you do that without stopping the cache?

Those are the types of questions you want to consider. 

How many of these configuration changes really require the cache to be restarted? 

The fewer, the better. Other than the caching features, the first criteria for having a cache that can run in a production environment is how much uptime the cache is going to give you.


### 11. Performance

Simply put, if accessing the cache is not faster than accessing your database, there is no need to have it. Having said that, what should you expect in terms of performance from a good distributed cache?

The first thing to remember is that a distributed cache is usually OutProc or remote, so access time will never be as fast as that of a stand-alone InProc cache (for example, ASP.NET Cache). 

In an InProc stand-alone cache, you can probably read 150,000 to 200,000 items per second (1KB object size). With an OutProc or a remote cache, this number drops significantly. 

In terms of performance, you should expect about 20,000 to 30,000 reads per second (1KB object size) as the throughput of an individual cache server (from all clients hitting on it). 

You can achieve some of this InProc performance by using a client cache (in InProc mode), but that is only for read operations and not for write operations. 

You sacrifice some performance in order to gain scalability, but the slower performance is still much faster than database access.