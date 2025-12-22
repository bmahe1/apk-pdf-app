@if "%DEBUG%" == "" @echo off
@rem
@rem Copyright 2015 the original author or authors.
@rem ... [file content as previously provided]
@rem
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %CMDN_ARGS%
