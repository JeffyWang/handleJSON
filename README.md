**题目：**
 1. 用Java编程完成以下功能:
      - a.提取出SD_DOC=0000000151, PARTN_ROLE=AG的 ORDER_PARTNERS_OUT 子节点
      - b.统计一下SD_DOC=0000000151一共出现了几次
      - c.将SD_DOC=0000000151, PARTN_ROLE=AG的 ORDER_PARTNERS_OUT 子节点的LEVEL_NR设置为100, 并将结果保存在另一个json文件中
 2. 尝试将处理json数据的代码改为更为抽象和可重用的代码，使代码能够作为一个工具类，能够满足相对通用的数据处理需求。
 3. 改进已有的代码，考虑如果需求1中需要处理的单个json文件容量过大的情况，如何能够高效的处理。
 4. 改进已有的代码，考虑如果需要处理的json文件数目很多的情况，如何能够高效的处理。


----------


**说明：**


1. 1-4题分别对应project包内的quiz1-quiz4，每个包内分别有其入口程序用于测试，命名规则是Quiz1Test - Quiz4Test.
2. 其中第2题做成了工具类，查询条件用Map来封装，key代表field字段名，value代表字段的值，实现了多条件查询。
3. 其中第4题用到了多线程处理，创建了线程池，用CompletionService进行管理，实现了Callable接口，可以方便的得到线程的返回值；
4. 第3题针对大文件进行了拆分，然后用第4题的多线程思想进行处理。

时间仓促，还有很多设计的不好的地方和bug，欢迎指正，谢谢。
