val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation(project(":perfume-core"))
    implementation(project(":perfume-support"))

    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    implementation("org.springframework.boot:spring-boot-starter-web:3.2.1")

    implementation("org.springframework.boot:spring-boot-starter-validation:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.2.1")

    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.2.1")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("org.flywaydb:flyway-mysql:9.22.3")

    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.2.1")
    compileOnly("org.projectlombok:lombok:1.18.30")

    annotationProcessor("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.2.1")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api:2.1.1")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    testImplementation("com.h2database:h2:2.2.224")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("org.springframework.security:spring-security-test:6.2.1")

    implementation(platform("software.amazon.awssdk:bom:2.17.230"))
    implementation("software.amazon.awssdk:s3:2.20.68")

    // batch
    implementation ("org.springframework.boot:spring-boot-starter-batch")

    // ES 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
//    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.17.9")
//    implementation("org.elasticsearch.client:elasticsearch-rest-client:8.6.2")
//    implementation("org.elasticsearch:elasticsearch:8.6.2")

    // 모니터링을 위한 의존성 (프로메테우스, actuator)
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("io.micrometer:micrometer-registry-prometheus")

    // circuit breaker
    implementation ("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:3.1.0")


    // implementation ("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

//    implementation ("io.github.resilience4j:resilience4j-all")

    // circuit breaker (2)-
//    implementation ("io.github.resilience4j:resilience4j-spring-boot3")
//    implementation ("org.springframework.boot:spring-boot-starter-actuator")
//    implementation ("org.springframework.boot:spring-boot-starter-aop")

    // circuit breaker (2)- webflux 사용하는 경우 필요
    // implementation 'io.github.resilience4j:resilience4j-reactor'
}


// task.jar 비활성화
// Gradle은 프로젝트 빌드 도구로, Task를 사용하여 빌드 과정을 정의하고 실행한다.
// task.jar는 jar 파일을 생성하는 task이다. 기본적으로 gradle은 프로젝트 소스 코드를 컴파일해서 jar로 패키징한다.
// 여기서 false는 비활성화 하는것이고, jar 파일을 생성하는 과정에서 실행하지 않는 것이다.
// 즉 gradle 빌드 시 jar 파일을 생성하지 않는다.
tasks.jar {
    enabled = false
}

val snippetsDir by extra {
    file("build/generated-snippets")
}

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    doFirst {
        delete("src/main/resources/static/docs")
    }
    inputs.dir(snippetsDir)
    configurations(asciidoctorExt.name)
    dependsOn(tasks.test)
    doLast {
        copy {
            from("build/docs/asciidoc")
            into("src/main/resources/static/docs")
        }
        copy {
            from("build/docs/asciidoc")
            into("build/resources/main/static/docs")
        }
    }
}

tasks.bootJar {
    dependsOn(tasks.asciidoctor)
}
