import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

var springBootVersion = ""
var url = ""


plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    //    Aplica o plugin que irá gerar a imagem do docker
    id("com.palantir.docker") version "0.22.1"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
}

//Esse processo de build será feito quando formos gerar a imagem do docker
buildscript {
//    ext {
//        springBootVersion = "2.5.5-SNAPSHOT"
//    }

//repositórios que vamos utilizar
    repositories {
        mavenCentral()
        maven {
            url =  uri("https://plugins.gradle.org/m2/")
        }
    }
    //Bibliotecas que serão usadas durante o processo de build
    dependencies {
//        Biblioteca do spring boot
//        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.5.5-SNAPSHOT")
//        Biblioteca que irá criar tarefas no intellij para poder executar a criação da imagem do docker
        classpath("gradle.plugin.com.palantir.gradle.docker:gradle-docker:0.22.1")
    }
}

//Adiciona o id do docker hub
group = "fcgeovane"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11


////Início da configuração que irá gerar a imagem do docker baseado com as informações do projeto


// Gradle Docker plugin configuration
// Please make sure you login to Docker registry before running Docker related tasks
docker {
    // All the build process should be passed before we run Docker related tasks
    dependsOn(tasks.getByName("build"))

    val bootJar: BootJar by tasks

    // Please specify the image metadata here
    val userName = "fcgeovane"
    val version = "1.2.0"
    name = "$userName/${project.name}:$version"

    // Please add the tags if you need more registries/userNames/tags.
    // Accordingly this plugin will create a corresponding task to tag/push it.
    //
    // By default, the registry to which it will push when you run "dockerPush" task is "docker.io".
    // So practically the following are not needed.
    //
    // val registry = "docker.io"
    // tag("DockerIO", "$registry/$name")

    // Set the path to Dockerfile
    setDockerfile(file("Dockerfile"))

    // Add the built jar file to Docker's build context
    files(bootJar.archiveFile)

    // Set buildArgs of Dockerfile
    buildArgs(mapOf(
        "JAR_FILE" to bootJar.archiveFileName.get()
    ))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.amazonaws:aws-java-sdk-sqs:1.11.887")
    implementation("org.springframework:spring-jms:5.2.9.RELEASE")
    implementation("com.amazonaws:amazon-sqs-java-messaging-lib:1.0.8")

    implementation ("com.amazonaws:aws-java-sdk-dynamodb:1.11.490")
    implementation ("com.github.derjust:spring-data-dynamodb:5.1.0")



    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}