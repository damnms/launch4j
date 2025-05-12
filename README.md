This is a copy of https://sourceforge.net/projects/launch4j/

I will only upgrade deps and see if i can migrate it to gradle.

***

## Versioning
The original version is maintained at https://sourceforge.net/projects/launch4j/, so i will apply changes from sf.net to this repository.  
That means, i have to have a different versioning, so users can track changes. Therefore, the versioning will be:  
`$sf_version.$major.$minor.$patch`, for example: 3.50.1.0.0, which means the base is launch4j 3.50, with my applied changes 1.0.0.

## Development
If you want to contribute or just check out the source and build it yourself, checkout the source and run ./gradlew $task. 
Depending on what you want to achieve, there are several tasks that are available, for example to build everything:  
```./gradlew bin_linux32DistZip bin_linux64DistZip bin_windows32DistZip bin_macosx_x86DistZip```  
This will produce the distributions in build/distributions. For further information see the gradle documentation.



