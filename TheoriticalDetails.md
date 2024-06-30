1.	Abstraction:  
Abstraction in OOP hides the implementation details and shows only the functionality.   It is in charge of hiding the non-essential details from the user.   The 'abstract' keyword is used to signify abstract classes.  
2.	Encapsulation:   
Encapsulation binds data and functions manipulating the data into a single unit.   It wraps the data and the functions that operate on the data into a single object.   It is also used for data hiding.  
3.	Classes and Objects:   
Classes are blueprints for creating objects in OOP.   They contain data and methods to interact with the data.   Objects are instances of classes.  
4.	Class creation:   
Classes are defined by using the 'class' keyword followed by the class name and a pair of curly braces containing properties and methods.  
5.	Object creation:   
An object is created by declaring an instance of a class.   You can create an object using the 'new' keyword followed by the class name and parentheses.  
1.	Access Modifiers:   
Access modifiers determine the scope of a class, constructor, variable, method or data member.   Examples of access modifiers are private, public, and protected.  
2.	Constructors:   
A constructor is a block of codes that initializes the newly created object.   It's declared like a normal function but shares the same name as the class.  
6.	Default Constructor:   
The compiler provides a default constructor if one isn't declared in the class.   It doesn't take any arguments.  
7.	Copy Constructor:   
It is a constructor that is called when a new object is created as a copy of an existing object.  
8.	Constructor Overloading:   
If a class has multiple constructors that differ in their parameter list, it is called constructor overloading.  
9.	Deep copy vs Shallow copy:   
A shallow copy duplicates as minimum data as possible.   If a field is a value type, it copies the bit-by-bit value of the field.   If a field is a reference type, it copies the reference but not the referred object.   Conversely, a deep copy occurs when an object is copied along with the objects to which it refers.  
10.	Pass by Reference vs.   Pass by Value:   
Pass by reference means to pass the reference of the argument in the function.   Pass by value means the actual value of the argument is copied into the formal parameter of the function.  
11.	Inheritance:   
Inheritance is a feature of OOP that allows a class to inherit properties and behaviors from another class.   The class whose properties are being inherited is called the "superclass" or "parent class", and the class that is doing the inheriting is called the "subclass" or "child class".  
12.	Constructor Chaining:   
It is the process of calling one constructor from another constructor with respect to the current object.   
13.	Polymorphism:   
Polymorphism allows methods to do different things based on the object that they are acting upon.   Runtime polymorphism and compile-time polymorphism are the two types of polymorphisms.  
14.	Runtime Polymorphism:   
It's a process in which a function call to an overridden method is resolved during runtime, not at compile-time.   
15.	Compile Time polymorphism:   
It's a process in which a function call to an overridden method is resolved during compile time.   This is done using method overloading and operator overloading.  
16.	Method Overloading:   
It's a feature that allows a class to have two or more methods having the same name, if their argument lists are different.  
17.	Method Overriding:   
It's a feature that allows a subclass to provide a specific implementation of a method that is already provided by its parent class.  
18.	Interfaces:   
Interfaces are a completely abstract class which can have only abstract methods.  
19.	Abstract Class:   
An abstract class is a class that cannot be instantiated.   It can have both abstract (methods that don't have a body) and non-abstract methods (regular methods with a body).  
20.	Static:   
Static means one per class, not one for each object no matter how many instances of a class might exist.  
21.	Final:   
The 'final' keyword in java is used to restrict the user.   It can be used with class, method, and variable.   If a class is declared as final, it can't be extended, if a method is declared as final, it can't be overridden and if a variable is declared final, it can be initialized only once in its lifetime.  
22.	Process Vs Thread:  
A process is an independent program running in the system having its own memory space.   It is separate from other processes and each process can contain multiple threads.   A thread is a lightweight subprocess that shares the same memory space with other threads in the same process.  
23.	Multicore Systems:  
Multicore systems consist of multiple cores (CPUs) in a single processor.   They execute instructions from multiple processes simultaneously, greatly increasing performance.   This is especially beneficial for applications that are programmed to work in a multithreaded or parallel processing environment.  
24.	Concurrency Vs Parallelism:  
Concurrency means dealing with a lot of things at the same time(i.  e.  , the ability of a system to run two tasks virtually at the same time), Parallelism means doing a lot of things at the same time(i.  e.  , using multiple processors/cores to execute tasks simultaneously).  
25.	Context Switching:  
It is the procedure during which the state of a process is saved so that it can be restored and execution can be resumed from the same point later.   This allows for multitasking where multiple processes share a single CPU.  
26.	Generics:  
Generics enable types (classes and interfaces) to be parameters when defining classes and methods.   It provides a way for ensuring type safety.   The idea is to allow type (Integer, String, … etc and user-defined types) to be a parameter to methods, classes and interfaces.   
27.	Function Interface:  
A functional interface is an interface that contains only one abstract method.   They can be used anywhere where an object is expected.   They can be used to declare variables, as method parameters, or as return types.  
28.	Lambda Functions:  
Lambda expressions provide a clear and concise way to represent one method interface using an expression.   Lambda expressions are used primarily to define inline implementation of a functional interface.  
29.	Streams:  
It is a sequence of objects which supports various methods that can be pipelined to produce the desired result.   Terminal methods terminate a stream pipeline and produce a result.   Intermediate methods transform a stream into another stream and can be connected together.  
Intermediate methods:   map(), filter(), sorted()
Terminal methods:   collect(), foreach(), reduce()
30.	Parallel Streams:  
As the name suggests, it leverages multiple processors/cores of the computer in order to perform operations on the stream objects faster as these operations are done parallely.  
31.	Functional Programming using Java:  
It allows us to write our code using Declarative approach.   It was introduced in Java 8.   It simplifies your task by providing facility of ready to use methods, classes etc.   They are immutable, they rely heavily on recursion, and they are inherently type-safe.  
32.	Exception Handling:  
It is a mechanism that prevents normal flow of program execution.   It is a method to handle runtime errors such as ClassNotFoundException, IOException, SQLException, RemoteException, etc.  
33.	Strings in Java:  
It is basically an object that represents sequence of char values.   Java String is immutable i.  e.   it cannot be changed once created.  
34.	Garbage collection:  
It is a process of reclaiming the runtime unused memory automatically.   In other words, it is a way to destroy the unused objects.  
35.	Memory Management in Java:  
The JVM divides its memory into method area, heap area, stack area, constant pool, native method stacks.   It keeps track of each and every object created in the system and takes necessary action to reclaim the memory of objects that are no longer in use.  
36.	Multi-Threaded programming & Thread Creation
Multithreaded programming involves two or more parts of the program, or threads, which can run concurrently.   The `start()`, `wait()`, and `sleep()` methods are used to manage and control these threads.  
37.	Thread pool
A pool of worker threads available to perform tasks.   It reuses threads, preventing the overhead of thread creation and destruction, increasing overall performance of the application.  
38.	Thread lifecycle:  
New:   created and not yet started.  
Runnable:   when ready to run, either running or waiting for its turn.  
Waiting:   waiting for another thread
Timed Waiting:   Waiting for a specific interval.  
Terminated:   completed execution.  
39.	Executors:   
They provide a pool of threads and API for assigning tasks (Runnable and Callable objects) to it.  
40.	Runnable and Callables:   
Interfaces to represent task, Runnable doesn't return result whereas Callable does.  
41.	Futures:  
Represents a computation that hasn't necessarily completed yet.   It provides methods to check if the computation has completed, to wait for its completion and to retrieve results.  
42.	Volatile keyword:   
Guarantees the visibility of changes to variables across threads.  
43.	Synchronization Issue (Adder / Subtractor Example):  
Concurrency can often lead to synchronization issues such as race conditions, deadlocks etc.  
44.	Mutex:   
Mutual Exclusion(Mutex) is a program object that prevents simultaneous access to a shared resource.  
45.	Synchronized keyword, methods:   
The synchronized keyword in Java is used to lock an object for any shared resource.  
46.	Semaphores:  
A class that controls access to resources, having a counter.  
47.	Concurrent Data Structures:  
Data structures that can be safely used by multiple threads concurrently e.  g ConcurrentHashMap, CopyOnWriteArrayList etc.  
48.	Atomic data types:  
Types which operations aren't interruptible.   
49.	Collection framework:   
It provides a standard architecture to manage and manipulate groups of objects.  
50.	Hierarchy of Collection Framework:  
It includes interfaces like Set, List, Queue, Deque and classes like ArrayList, Vector, LinkedList, PriorityQueue, HashSet, LinkedHashSet, TreeSet etc.  
51.	HashSet vs HashMap:  
Both stores unique values but HashMap stores key-value pair whereas HashSet only stores value.  
52.	TreeMap:  
Stores key-value pairs in sorted order
53.	LinkedHashMap:  
Cache based Map where elements can access in their insertion order.  
54.	Vector:   
It’s similar to ArrayList but it is synchronized.  
55.	ConcurrentHashMap:  
A class from java.  util.  concurrent package which provides Thread safety, high concurrency while maintaining performance.


