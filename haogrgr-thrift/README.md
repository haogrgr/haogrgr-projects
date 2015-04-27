Thrift使用
==

1. Thrift主要
--

####(1)Thrift网络堆

+-------------------------------------------+
| Server                                    |
| (single-threaded, event-driven etc)       |
+-------------------------------------------+
| Processor                                 |
| (compiler generated)                      |
+-------------------------------------------+
| Protocol                                  |
| (JSON, compact etc)                       |
+-------------------------------------------+
| Transport                                 |
| (raw TCP, HTTP etc)                       |
+-------------------------------------------+

####(2)Transport层
- 传输层, 为网络的读/写提供一个简单的抽象.使Thrift解耦底层的传输和其他的系统(例如:序列化/反序列化)
- 核心接口`TTransport`, 主要方法
> `isOpen`, `open`, `close`, `read`, `write`


2. red