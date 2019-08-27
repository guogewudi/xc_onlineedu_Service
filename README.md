#RabbitMQ实战cms client（消费者）和cms（生产者） directExchange类型的交换机
#postpage函数：生产者:1.通过pageId，来实现对页面的静态化

（页面静态化：通过pageId，获取templateId，通过templateId获取templatefieldId，
也就是模板的ID，和gridfs存文件的id相同，找到了这个模板，再用模板中的freemaker表达式来获取之前
dataurl中的getmodle中的value字段，此value字段就是发布在nginx上的前端图片，这样模板中的freemaker字段都替换成了前段图片地址，
然后将此页面静态化完毕）
2.静态化完毕之后，将此文件存放在gridfs中，并且生成了唯一的htmlfield字段。与mongodb中文件id字段相同
3.发送消息：sendpostpage函数接收此pageId，首先判断pageId是否真实有效，
           然后查数据库获得该page的站点信息作为路由key。
           用rabbitmqtempalate将此消息发送到此路由key站点
           
#消费者：
消费者通过@Rabbitlistener注解监听消息队列。收到了消息（config里已经从配置文件yml获取了交换机
和路由key，当然和生产者是相同的路由key）。将此消息转成json格式从而获得其中的pageId数据。
当获得了pageId字段以后，查数据库，获得了页面物理地址+服务器物理地址+页面名称以及生产者写入的htmlfield。
通过htmlfield获得了文件id（他俩是一个东西）从而使用gridfs下载到了这个文件的inputstream。将此流获得的内容复制在了
刚才获得的路径中。我们会发现目标文件夹里就是我们client自己从gridfs（mongodb）中下载的文件。

总结：生产者生成了html文件和并把相关的htmlfield存入了数据库
消费者通过htmlfield下载到了此文件到目标文件夹  其中消费者和生产者通信的消息队列不在阐述前文讲的很详细。
