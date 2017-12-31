pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        git(url: 'https://github.com/yaichZied/WS_Project.git', branch: '${BRANCH_NAME}', poll: true, changelog: true)
      }
    }
  }
}