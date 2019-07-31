Mock Json
----
[![](https://www.jitpack.io/v/landscapeside/MockNetApi.svg)](https://www.jitpack.io/#landscapeside/MockNetApi)

### feature
##### v1.0

调用方式原始，适用于非注解类网络请求方式，如AsyncHttpClient

##### v2.1

**仅仅**支持retrofit和rxjava2

### Usage

##### v1.0

* 引入

项目build.gradle添加jitpack仓库

  ```groovy
    allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}
  ```
应用build.gradle添加依赖

  ```groovy
  implementation "com.github.landscapeside:MockNetApi:v1.0.2"
  ```

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
	
##### v2.0

* 引入

项目build.gradle添加jitpack仓库

  ```groovy
    allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}
  ```
应用build.gradle添加依赖

  ```groovy
  implementation "com.github.landscapeside:MockNetApi:2.1.0"
  ```
  
* 注解

  ```java
  public interface UserApi {
    //通过MOCK注解申明该接口mock文件存放路径
    @MOCK("account/user_info")
    @GET("account/user_info")
    Flowable<UserBean> requestUser(@Path("user_name") String userName);
  }
  ```
* 准备数据json文件

	将以下内容保存在user_info.json文件中，并保存在android项目的**assets/mockdata/account**中（没有文件夹就需要新建）

  ```json
		{
		  "name":"nick",
		  "sex":"man",
		  "age":18,
		  "jobs":1
		}
  ```
  
* 构建MockApi对象，最好单例
  ```java
  MockApi mockApi = MockApi.builder().context(appContext).delayMilliSeconds(3000).build();
  ```

* 模拟网络请求

  ```java
  UserApi userApi = mockApi.create(UserApi.class);
  userApi.requestUser(userName).subscribe(new Consumer<UserBean>() {
    @Override public void accept(UserBean userBean) throws Exception {
      tvResult.setText(userBean.toString());
    }
  }, new Consumer<Throwable>() {
    @Override public void accept(Throwable throwable) throws Exception {
      Toast.makeText(MockMainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
  }, new Action() {
    @Override public void run() throws Exception {
      Toast.makeText(MockMainActivity.this, "complete", Toast.LENGTH_LONG).show();
    }
  });
  ```





