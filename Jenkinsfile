pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        git(url: 'https://github.com/yaichZied/WS_Project.git', branch: '${BRANCH_NAME}', poll: true, changelog: true)
      }
    }
    stage('Packaging') {
      steps {
        sh 'mvn clean'
        sh 'mvn install'
        sh 'sudo fuser -k 80/tcp; exit 0'
        sh 'sudo mvn spring-boot:run'
      }
    }
  }
  
}
