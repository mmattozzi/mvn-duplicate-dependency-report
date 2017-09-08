Run `mvn clean install` in the project directory. 
Run `mvn org.restlesscode:duplicate-dependency-report:find` in your project directory.

If you want to shorten that command:

Modify ~/.m2/settings.xml and add org.restlesscode to the list of pluginGroups. 

```
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/settings/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://mav
en.apache.org/xsd/settings-1.0.0.xsd">

  <pluginGroups>
    <pluginGroup>org.restlesscode.plugins</pluginGroup>
    <pluginGroup>org.restlesscode.maven</pluginGroup>
  </pluginGroups>
  ...
```

Then run `mvn duplicate-dependency-report:find` in your project directory.
