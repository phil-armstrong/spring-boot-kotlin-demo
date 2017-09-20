import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpmTask

buildscript {
	repositories {
		mavenCentral()
	}
}

plugins {
	val kotlinVersion = "1.1.4-3"
	id("org.jetbrains.kotlin.jvm") version kotlinVersion
	id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
	id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
	id("org.springframework.boot") version "1.5.6.RELEASE"
	id("com.moowork.node") version "1.2.0"
}

apply {
	plugin("kotlin")
	plugin("kotlin-spring")
	plugin("kotlin-jpa")
	plugin("org.springframework.boot")
	plugin("com.moowork.node")
}

version = "0.0.1-SNAPSHOT"

configure<JavaPluginConvention> {
	setSourceCompatibility(1.8)
	setTargetCompatibility(1.8)
}

repositories {
	mavenCentral()
}

dependencies {
	compile("org.springframework.boot:spring-boot-starter-web")  {
        exclude(module = "spring-boot-starter-tomcat")
    }
	compile("org.springframework.boot:spring-boot-starter-jetty")
	compile("org.springframework.boot:spring-boot-starter-data-jpa")
	compile("com.h2database:h2")
	compile("org.jetbrains.kotlin:kotlin-stdlib")
	compile("org.jetbrains.kotlin:kotlin-reflect")
	compile("com.fasterxml.jackson.module:jackson-module-kotlin")
	compile(group = "org.apache.solr", name = "solr-solrj", version = "6.3.0")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}

//task buildClientWatch(type: NpmTask, dependsOn: 'npmInstall')
//{
//    group = 'application'
//    description = "Build and watches the client side assets for rebuilding"
//    args = ['run','buildWatch']
//}