@echo "Starting Appium !!!"
C:
taskkill /F /IM node.exe
cd %APPIUM_HOME%node_modules\appium\bin
"%APPIUM_HOME%node.exe" "%APPIUM_HOME%node_modules\appium\bin\Appium.js" --app C:\Users\anish_lingambhotla\Downloads\vApprove_TEST_7_OCT_2015.apk --address 127.0.0.1 --chromedriver-port 9516 --bootstrap-port 4725 --selendroid-port 8082 --no-reset --local-timezone --device-name 052b0137002e3a65

