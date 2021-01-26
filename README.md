# interlok-service-tester
[![GitHub tag](https://img.shields.io/github/tag/adaptris/interlok-service-tester.svg)](https://github.com/adaptris/interlok-service-tester/tags) ![license](https://img.shields.io/github/license/adaptris/interlok-service-tester.svg) [![codecov](https://codecov.io/gh/adaptris/interlok-service-tester/branch/develop/graph/badge.svg)](https://codecov.io/gh/adaptris/interlok-service-tester) [![Known Vulnerabilities](https://snyk.io/test/github/adaptris/interlok-service-tester/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/adaptris/interlok-service-tester?targetFile=build.gradle) [![Dependabot Status](https://api.dependabot.com/badges/status?host=github&repo=adaptris/interlok-service-tester)](https://dependabot.com)

Interlok service tester is a testing framework that allows you to unit test Interlok configurations.

The aim is to remove the need to have working consumers and producers but still be able to test the logic within Interlok configuration, such as branching and mappings.

The output of the execution has also been designed to drop into existing continuous integration cycles.

## Execution

The simplest way to run your service test config is via interlok-boot:

```
C:\Adaptris\Interlok>java -jar lib\interlok-boot.jar -serviceTest config\service-test.xml
WARN  [main] [Adapter] [Adapter] has a MessageErrorHandler with no behaviour; messages may be discarded upon exception
INFO  [main] [Adapter] Adapter Initialised
INFO  [main] [Adapter] Adapter Started
INFO  [main] [Test] Running [TestList.Test1]
INFO  [main] [Test] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.019 sec
INFO  [main] [Adapter] stopping adapter [e05d61a7-af13-498b-a669-0b34558afd47] if there are polling loops configured this may take some time
INFO  [main] [Adapter] Adapter Stopped
INFO  [main] [Adapter] Adapter Closed
```

## Arguments

The following arguments are available to customise service tester execution:

- **serviceTest:** Test configuration location
- **serviceTestOutput:** Output directory for test results (default: ./test-results)
- **serviceTestPreProcessor:** Pre-processors to execute against test configuration (ex: xinclude)


Mor information can be found at https://interlok.adaptris.net/interlok-docs/#/pages/service-tester/
