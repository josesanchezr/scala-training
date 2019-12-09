import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(

      name := "scala-training",
      mainClass in (Compile, run) := Option("com.example.EmployeeApp"),

      libraryDependencies ++= Seq(
          cats,
          slick,
          mysql,
          hikaricp,
          flywaydb,
          akkaHttp,
          akka,
          akkaActor,
          akkaCirce,
          circeGeneric,
          circeParser,
          pureConfig,
          swagger,
          monix,
          kafka,
          monixKafka,
          "org.apache.logging.log4j" % "log4j-1.2-api" % "2.8.2",
          "org.apache.logging.log4j" % "log4j-api" % "2.11.1",
          "org.apache.logging.log4j" % "log4j-core" % "2.11.1",
          "org.apache.logging.log4j" % "log4j-jcl" % "2.11.1",
          "org.apache.logging.log4j" % "log4j-jul" % "2.11.1",
          "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.11.1",
          scalaTest % Test).map(_ withJavadoc () withSources ()), //download the javadoc and sources from each dependency

      scalacOptions ++= Seq(
          "-language:implicitConversions",
          "-language:postfixOps",
          "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
          "-Ywarn-unused:locals", // Warn if a local definition is unused.
          "-Ywarn-unused:params", // Warn if a value parameter is unused.
          "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
          "-Ywarn-unused:privates", // Warn if a private member is unused.
          "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
      )

  )