# rpc
主要是个人实现一个从简单到复杂的RPC框架流程记录。

##### 环境：
* JDK 1.8_261
##### 包含依赖：
* netty-all 4.1.51.Final （第四次提交引入）
* fastjson 1.2.70 （第五次提交引入）
* curator-recipes 5.1.0 （第六次提交引入）

##### 提交记录：
* *20210521* 第一次提交，实现了一个简单的RPC；
* *20210524* 第二次提交，通过反射和动态代理，抽象了请求和响应，可以调用Service的不同方法，同时返回不同对象；
* *20210527* 第三次提交，重构服务端代码，经过在ServiceProvider注册可以调用不同Service的不同方法，返回不同对象，同时提供了简单线程和线程池两种形式的RPC服务端；
* *20210528* 第四次提交，对RPC性能进行提升，引入Netty网络框架，将网络传输从BIO到NIO：重构客户端代码，提供了简单版本和Netty版本的RPC客户端；对服务端引入Netty版本，实现了Netty通信。
* *20210529* 第五次提交，改进了java自带的低性能序列化方式。Netty自带以 消息长度（4个字节）| 序列化后的数据 格式简单传输数据的编解码器，而这次更新则是采用了自定义传输格式的编解码器，包含Java原生和Json两种序列化器，进一步提高效率，同时更新了日志显示。
* *20210602* 第六次提交，针对客户端调用服务时必须知道服务端host和port的问题，引入了zookeeper注册中心，构成了完整的RPC框架：服务提供者，服务消费者，注册中心。
* *20210602* 第七次提交，针对一个服务有多个提供者支持时，解决分散服务提供者压力的问题，引入了负载均衡技术，包含随机负载均衡和轮询负载均衡技术。
