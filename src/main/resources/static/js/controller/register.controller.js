'use strict';

angular.module('app')
    .controller('RegisterController', function ($scope, Auth) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.user = {};
        $scope.roles = ['ROLE_ADMINISTRATOR','ROLE_USER'];

        /*$timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});*/

        /*$scope.matchPass=function(pass,conPass){

            if(pass != conPass){
                $scope.notMatched=true;
            }else{
                $scope.notMatched=false;
            }

        };*/

        $scope.register = function () {
            console.log('click');
            if ($scope.user.plainPassword !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
                console.log('click');

            } else {
               // $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                console.log($scope.user)
                // $scope.user.activated = true;
                //$scope.user.activated = true;
                //$scope.user.enabled = true;
                //$scope.user.plainPassword = $scope.user.password;
                //$scope.user.roles = ["ROLE_USER"];

                Auth.createAccount($scope.user).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    });
