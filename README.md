## Setting
How to implement library
- Step.1 Add it in your root build.gradle at the end of repositories:
    	
      allprojects {
        repositories {
          ...
          maven { url 'https://jitpack.io' }
        }
      }
- Step 2. Add the dependency
    
      implementation 'com.github.blelalr:AppCore:library-social-login-SNAPSHOT'

Create your own develop project
- [Facebook developer](https://developers.facebook.com/)
- [Google console](https://console.cloud.google.com/)
- [Line developer](https://developers.line.biz/)

Add local.properties to root

    facebook_app_id="your_facebook_app_id"
    fb_login_protocol_scheme="your_fb_login_protocol_scheme"
    google_default_web_client_id="your_google_default_web_client_id"
    line_channel_id="your_line_channel_id"
    
How to use 

    Auth.login()
    
    Auth.logout()
    
    Auth.getUserInfo()
