Setup
=====
* Run `mvn clean install` in the project directory. 
* Run `mvn org.restlesscode:duplicate-dependency-report:find` in your project directory.

Example Output
==============
```
[INFO] --- duplicate-dependency-report:1.0.0-SNAPSHOT:find (default-cli) @ very-dogeon ---
Multiple versions detected for org.hamcrest:hamcrest-core
  1.1 - Omitted
  1.3 - Included
  Paths to 1.1 dependencies:
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.mockito:mockito-core:jar:1.9.5:test
          \ org.hamcrest:hamcrest-core:jar:1.1:test Omitted for conflict
  Paths to 1.3 dependencies:
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        \ org.hamcrest:hamcrest-core:jar:1.3:test Included

Multiple versions detected for org.scala-lang:scala-library
  2.10.4 - Omitted
  2.10.3 - Included
  2.10.1 - Omitted
  Paths to 2.10.4 dependencies:
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.scalacheck:scalacheck_2.10:jar:1.11.3:test
          \ org.scala-lang:scala-library:jar:2.10.4:test Omitted for conflict
  Paths to 2.10.3 dependencies:
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      \ org.scala-lang:scala-library:jar:2.10.3:compile Included
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.scala-lang:scala-compiler:jar:2.10.3:test
          \ org.scala-lang:scala-library:jar:2.10.3:test Omitted for duplicate
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        \ org.scala-lang:scala-library:jar:2.10.3:test Omitted for duplicate
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.scalamacros:quasiquotes_2.10.3:jar:2.0.0-M3:test
          \ org.scala-lang:scala-library:jar:2.10.3:test Omitted for duplicate
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.scala-lang:scala-reflect:jar:2.10.3:test
          \ org.scala-lang:scala-library:jar:2.10.3:test Omitted for duplicate
  Paths to 2.10.1 dependencies:
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.scalaz:scalaz-core_2.10:jar:7.0.6:test
          \ org.scala-lang:scala-library:jar:2.10.1:test Omitted for conflict
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.scalaz:scalaz-concurrent_2.10:jar:7.0.6:test
          \ org.scala-lang:scala-library:jar:2.10.1:test Omitted for conflict
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        + org.scalaz:scalaz-concurrent_2.10:jar:7.0.6:test
          + org.scalaz:scalaz-effect_2.10:jar:7.0.6:test
            \ org.scala-lang:scala-library:jar:2.10.1:test Omitted for conflict

Multiple versions detected for junit:junit
  4.8.2 - Included
  4.11 - Omitted
  Paths to 4.8.2 dependencies:
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      \ junit:junit:jar:4.8.2:test Included
  Paths to 4.11 dependencies:
    + wow.much.pigppo.doge:very-dogeon:jar:1.0-SNAPSHOT
      + org.specs2:specs2_2.10:jar:2.3.10:test
        \ junit:junit:jar:4.11:test Omitted for conflict
```

If you want to shorten that command:

Modify `~/.m2/settings.xml` and add `org.restlesscode` to the list of pluginGroups. 

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
