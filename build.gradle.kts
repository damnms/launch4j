plugins {
    id("distribution")
    id("application")
    id("maven-publish")
    id("com.gradleup.shadow") version "9.0.0-beta13"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://simulation.tudelft.nl/maven/")
    }
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("commons-beanutils:commons-beanutils:1.10.1")
    implementation("commons-logging:commons-logging:1.3.5")
    implementation("net.java.abeille:abeille:3.0") {
        exclude(group = "org.apache.batik", module = "com.springsource.org.apache.batik.ext.awt")
        exclude(group = "colt", module = "colt")
        exclude(group = "dom4j", module = "dom4j")
        exclude(group = "dsol", module = "dsol-xml")
        exclude(group = "javax.help", module = "javahelp")
        exclude(group = "jfree", module = "jfreechart")
        exclude(group = "jfree", module = "jcommon")
        exclude(group = "org.apache.mahout", module = "mahout-collections")
        exclude(group = "com.github.rwl", module = "optimization")
    }
    implementation("com.jgoodies:jgoodies-common:1.8.1")
    implementation("com.jgoodies:jgoodies-forms:1.9.0")
    implementation("com.formdev:flatlaf:1.6.5")
    implementation("com.thoughtworks.xstream:xstream:1.4.21")
    implementation("org.apache.ant:ant:1.10.15")
}

group = "net.sf.launch4j"
version = "3.50.1.0.1"
description = "Launch4j"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

distributions {
    create("bin_linux64") {
        distributionBaseName.set("${project.name}-linux64-${version}")
        contents {
            from(tasks.named("shadowJar"))
            into("w32api") {
                from(fileTree("w32api"))
            }
            into("w32api_jni") {
                from(fileTree("w32api_jni"))
            }
            into("head_jni_BETA") {
                from(fileTree("head_jni_BETA"))
            }
            into("head") {
                from(fileTree("head"))
            }
            into("bin") {
                from(fileTree("bin/bin-linux64"))
            }
            into(".") {
                from(file("launch4j.sh"))
            }
            into(".") {
                from(file("launch4jc.sh"))
            }
        }
    }

    create("bin_linux32") {
        distributionBaseName.set("${project.name}-linux32-${version}")
        contents {
            from(tasks.named("shadowJar"))
            into("w32api") {
                from(fileTree("w32api"))
            }
            into("w32api_jni") {
                from(fileTree("w32api_jni"))
            }
            into("head_jni_BETA") {
                from(fileTree("head_jni_BETA"))
            }
            into("head") {
                from(fileTree("head"))
            }
            into("bin") {
                from(fileTree("bin/bin-linux"))
            }
            into(".") {
                from(file("launch4j.sh"))
            }
            into(".") {
                from(file("launch4jc.sh"))
            }
        }
    }

    create("bin_windows32") {
        distributionBaseName.set("${project.name}-win32-${version}")
        contents {
            into("w32api") {
                from(fileTree("w32api"))
            }
            into("w32api_jni") {
                from(fileTree("w32api_jni"))
            }
            into("head_jni_BETA") {
                from(fileTree("head_jni_BETA"))
            }
            into("head") {
                from(fileTree("head"))
            }
            into("bin") {
                from(fileTree("bin/bin-win32"))
            }
            into(".") {
                from(file("launch4j.exe"))
            }
        }
    }

    create("bin_macosx_x86") {
        distributionBaseName.set("${project.name}-macosx_x86-${version}")
        contents {
            from(tasks.named("shadowJar"))
            into("w32api") {
                from(fileTree("w32api"))
            }
            into("w32api_jni") {
                from(fileTree("w32api_jni"))
            }
            into("head_jni_BETA") {
                from(fileTree("head_jni_BETA"))
            }
            into("head") {
                from(fileTree("head"))
            }
            into("bin") {
                from(fileTree("bin/bin-macosx-x86"))
            }
        }
    }
}

tasks {

    jar {
        manifest {
            attributes(
                "Main-Class" to "net.sf.launch4j.Main"
            )
        }
    }

    shadowJar {
        manifest.inheritFrom(jar.get().manifest)
        archiveClassifier = null
        version = ""
    }

    named("bin_linux64DistZip") {
        dependsOn("shadowJar")
    }

    named("bin_linux32DistZip") {
        dependsOn("shadowJar")
    }

    named("bin_windows32DistZip") {
        dependsOn("shadowJar", "createWindowsExe")
    }

    named("bin_macosx_x86DistZip") {
        dependsOn("shadowJar")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    register<JavaExec>("createWindowsExe") {
        dependsOn("shadowJar")
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("net.sf.launch4j.Main")
        jvmArgs("-Dlaunch4j.bindir=./bin/bin-linux")
        args("launch4j_config.xml")
    }
}

application {
    mainClass =
        "net.sf.launch4j.Main" //will make run & runShadow tasks work. Have no idea why it can't take main class from jar/shadowJar manifests
}