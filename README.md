Java client for service anti-captcha.com

Add to maven project:

1) add repository
```xml
<repositories>
    <repository>
        <id>anticaptcha-java-mvn-repo</id>
        <url>https://raw.github.com/staliang/anticaptcha-java/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

2) add dependency
```xml
<dependencies>
    <dependency>
        <groupId>com.anticaptcha</groupId>
        <artifactId>anticaptcha</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```