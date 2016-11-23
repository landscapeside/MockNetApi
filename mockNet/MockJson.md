Mock Json
----

在日常开发中，在开始一个新业务模块需求的时候，往往前端程序员（android OR IOS OR H5）是直接拿着设计图，需求文档以及接口文档就直接开始功能的实现，而此时后端程序员也处于同步开发的阶段，所以一般在前端和后台程序员都把响应的功能模块开发完成之后，需要按照接口文档来进行联调。

>***而因为联调阶段往往放在开发后期进行，所以这会导致前端在实现功能的时候更偏向于先做界面再完成逻辑，而往往逻辑又与界面紧密关联，这就导致了开发过程不通畅***

一般来说为了解决这样的困扰，程序员在开发功能模块的时候会按照接口文档的约定来mock数据，以完成本地功能模块的实现和界面效果的验证，然而在未借助工具或者辅助技巧的前提下，前端程序员会直接在项目源码里面通过动态生成测试数据的方式进行模拟，这种方式的优点是随机灵活，但是缺点却是在需要模拟的数据结构复杂的情况下模拟代码将会*急剧膨胀*，给代码的**整洁**带来极大的阻碍。

而就现有的网络交互基本都采用json方式以及javascript十分通用的前提，若是能通过直接定义json文件（编写模拟数据），然后在模块中直接读取出来以模拟网络返回，将会在几乎不影响源码的基础上模拟简单或者复杂的数据，以及在其他测试阶段可直接将json文件共享给测试人员或者后端程序员做相应的测试

####Usage

* 注册

		// 一旦请求URL中包含/accout/user，将在延迟3000毫秒之后以String的方式返回user.json中的内容
	    
		MockApi.addSuite(new ApiSuite("/accout/user", "account/user.json").setTimeDelay(3000));
	
		// 当然可以在添加了测试套件之后，通过MockApi.setTimeDelay设置全局延迟时间
	
		MockApi.addSuite(new ApiSuite("/accout/user", "account/user.json"));
		
		MockApi.addSuite(new ApiSuite("/accout/user1", "account/user.json"));
		
		MockApi.addSuite(new ApiSuite("/accout/user2", "account/user.json"));
	
		MockApi.setTimeDelay(3000);

* 模拟请求

		MockApi.mockRequest(context, "http://XXXXXX/accout/user", new MockApi.IMock() {
            @Override
            public void success(String response) {
                UserBean userBean = JSONS.parseObject(response, UserBean.class);
                tvResult.setText(JSONS.parseJson(userBean));
                count++;
                tvResult1.setText("count:" + count);
                tvResult2.setText("use time:" + (System.currentTimeMillis()-lastTime));
                lastTime = System.currentTimeMillis();
            }
        });

* 准备数据json文件

	将以下内容保存在user.json文件中，并保存在android项目的**assets/mockdata/account**中（没有文件夹就需要新建）

		{
		  "name":"nick",
		  "sex":"man",
		  "age":18,
		  "jobs":1
		}

* 销毁

	在使用完之后通过`MockApi.unInstall();`销毁（比如`onDestroy`中）

