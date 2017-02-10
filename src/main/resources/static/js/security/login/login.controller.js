'use strict';

angular.module('app')
    .controller('LoginController', function ($rootScope, $scope, $state,  Auth, $translate) {
        $scope.user = {};
        $scope.errors = {};
        console.log('click');

        $scope.rememberMe = true;
        /*$timeout(function () {
            angular.element('[ng-model="username"]').focus();
        });*/
        $scope.login = function (event) {

            console.log({username: $scope.username,
                password: $scope.password});
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
            }).then(function () {
                $scope.authenticationError = false;

                console.log('login Success');
                $state.go('home');

                //if ($rootScope.previousStateName === 'register') {
                //    $state.go('home');
                //} else if ($rootScope.previousStateName === 'register') {
                //} else {
                //    $rootScope.back();
                //}
            }).catch(function () {
                console.log('login Error');
                $scope.authenticationError = true;
            });
        };
    });
