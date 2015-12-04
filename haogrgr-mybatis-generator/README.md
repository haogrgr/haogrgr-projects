### Mybatis代码生成项目
***

#### 介绍

自用的代码生成工具, 基于Mybatis官方的generator项目, 通过其插件机制类生成`Model` `Mapper` `Service` 一系列类, 通过继承通用的`BaseMapper` `BaseService` 等类来实现单表的增删改查等常用的方法封装.

会生成带如下方法的Mapper, Service

```java
public interface BaseMapper<T, K> {

	/**
	 * 根据主键查找记录, 返回对应记录
	 */
	public T getById(K id);

	/**
	 * 根据主键批量查找对应的记录, 返回对应记录
	 */
	public List<T> getByIds(List<K> ids);

	/**
	 * 根据pojo熟悉查找记录, 返回对应记录
	 */
	public List<T> getByPojo(T pojo);

	/**
	 * 分页查询, 返回分页列表
	 */
	public <M> List<M> getByPageList(PageInfo<M> page);

	/**
	 * 分页查询, 返回总记录数
	 */
	public <M> Integer getByPageCount(PageInfo<M> page);

	/**
	 * 查询所有记录
	 */
	public List<T> getAll();

	/**
	 * 查询总记录数
	 */
	public Integer getAllCount();

	/**
	 * 修改记录, 返回修改的记录数
	 */
	public Integer modify(T record);

	/**
	 * 选择性的修改记录, 返回修改的记录数
	 */
	public Integer modifySelective(T record);

	/**
	 * 插入记录, 返回插入记录数(0 or 1)
	 */
	public Integer save(T record);

	/**
	 * mysql批量插入记录, 返回插入记录条数.
	 */
	public Integer saveBatch(List<T> records);

	/**
	 * 根据主键删除, 返回删除记录数
	 */
	public Integer delById(K id);

	/**
	 * 根据主键批量删除, 返回删除记录数, 注意: 联合主键, 不支持该操作
	 */
	public Integer delByIds(List<K> ids);

}
```

#### 例子

具体生成的代码, 可以以参考haogrgr-test里面的`model.TestModel` `dao.TestMapper` `service.TestService` 等类来查看生成后的代码的样子.

#### 目的

1. 生成的代码虽然也有增删改查等功能, 但是没有实现公共的接口, 代码重用不方便.

2. 生成的Mapper中有很多我不常用的方法, 比如ByExample等一系列方法.

3. 方法命名不喜, 个人比较喜欢精简, 形象的命名, 比如 `get` , `modify`, `save`, `del` 的命名方式.

4. 缺少了一些我常用的方法, 比如`批量插入`等方法.

5. 习惯Model中的set方法返回this来减少代码.

6. 习惯给Model中添加参数为主键的构造方法, 并给属性加上数据库注释.

7. 文件命名不习惯, 比如如果Model名为 `TestModel` , 则生成的Mapper文件名为 `TestModelMapper` , 而我想要的 `TestMapper` 强迫症, 没办法.

8. Service代码没有生成.

9. Mapper.xml文件中的方法排列顺序不习惯.

10. Mapper.xml中每个SQL后后面生成空行.

11. Mapper.xml中缩进是2个空格, 而我习惯的是4个空格.

12. Model代码中, 属性和属性之间有空行, 个人不习惯.

13. 你哪来的这么要求......

#### 具体实现

基于Mybatis官方的generator项目, 通过其插件机制来实现上面的目的, generator很灵活, 很多地方都留了扩展, 基本可以在不修改源码的基础上, 实现以上的要求.

1. 启动类:
	* com.haogrgr.mybatis.generator.main.Main
			main方法所在类, 运行根据配置生成代码

1. 源码修改点:
	* org.mybatis.generator.api.dom.java.InnerClass
			实现Model中Field之间不留空行

	* org.mybatis.generator.api.dom.OutputUtilities
			实现xml中缩进由2个空格变为4个空格

2. 扩展的类:
	* com.haogrgr.mybatis.generator.utils.SortedXmlFormatter
			实现Mapper.xml中方法排序和sql之间空行

3. 插件类
	* com.haogrgr.mybatis.generator.plugin.TableConfigPlugin
			代码的方式配置Table元素以禁用ByExample方式
			enableSelectByExample=false
			enableDeleteByExample=false
			enableCountByExample=false
			enableUpdateByExample=false
	
	* com.haogrgr.mybatis.generator.plugin.MapperPlugin
			Mapper文件处理
	
	*  com.haogrgr.mybatis.generator.plugin.ModelPlugin
			Model文件处理
	
	* com.haogrgr.mybatis.generator.plugin.ServicePlugin
			Service代码生成

#### 一些配置

配置文件为`resources/app-config.properties`中

配置项说明:

* 数据库配置
	1. `db.*` 类的配置, 为generator数据库配置, 不多说.


* generator配置文件配置
	1. `generator.common.target.project` 生成代码位置
	2. `generator.model.package` model类所在包
	3. `generator.mapper.xmlPackage` mapper.xml所在包
	4. `generator.mapper.javaPackage` mapper.java所在包
	5. `generator.service.servicePackage` service接口所在包
	6. `generator.service.serviceImplPackage` service实现类所在包


* 插件配置
	1. `plugin.common.toBeReplace` 去掉mapper中的指定字符串
			比如: 配置为plugin.common.toBeReplace=Model:
			则: TestModelMapper.java会变为TestMapper.java
	2. `plugin.model.generateSerialVersionField` model是否实现Serializable接口
	3. `plugin.model.setMethodReturnThis` model中set方法是否返回this
	4. `plugin.mapper.baseMapperName` BaseMapper类全名
	5. `plugin.service.generateIface` service是否生成接口(小项目没必要层层接口)
	6. `plugin.service.baseServiceName` BaseService接口类全名
	7. `plugin.service.baseServiceSupportName` BaseService通用实现
	8. `plugin.service.serviceImplNameSuffix` 接口实现类名后缀, 如Impl等

#### 项目总结

1. 我要的通用的实现, 我只需要满足自己的需要, 要实现通用, 就会有些地方需要妥协.

2. 代码划分, 最初的代码是14年写的, 所有逻辑都在一个插件类里面, 导致类代码很多, 虽然分了方法, 但是一打开, 一片的代码, 心智压力大, 后来按功能将代码分出去了, Model, Mapper, Service代码生成分为不同的插件, Mapper中新家的方法, 一个方法一个类, 重构完后心里舒服多了, 每次看到一大堆代码, 压力山大.

3. 约定大于配置.

4. 简单而美.
