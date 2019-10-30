'use strict';

angular.module('app')
    .controller('LoginController', function ($rootScope, $scope, $state,  Principal, Auth, $translate,blockUI) {
        $scope.user = {};
        $scope.errors = {};
        console.log('click');

        $scope.rememberMe = true;
        /*$timeout(function () {
            angular.element('[ng-model="username"]').focus();
        });*/
        $scope.login = function (event) {
            blockUI.start('Authenticating...');
            console.log({username: $scope.username,
                password: $scope.password});
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
            }).then(function () {
                $scope.authenticationError = false;

                console.log('login Success');
                blockUI.stop();
                $state.go('home');
                $rootScope.$broadcast("updateUserName");
                //NotificationPublisher.send('masumr')
                if ($rootScope.previousStateName === 'register') {
                    if(Principal.hasAnyAuthority(['ROLE_ADMINISTRATOR','ROLE_DG','ROLE_DG'])) {
                        $state.go('home',null, {reload:true});
                    } else {
                                              $state.go('home');
                                          }
                                  }
                              if (!$rootScope.previousStateName && $rootScope.previousStateName === 'login') {
                                      if(Principal.hasAnyAuthority(['ROLE_ADMINISTRATOR','ROLE_DG'])) {
                                              $state.go('home',null, {reload:true});
                                          } else {
                                              $state.go('home',null, {reload:true});
                                          }

                                      } else if ($rootScope.previousStateName === 'register') {
                                  } else {
                                      $rootScope.back();
                                  }

                //if ($rootScope.previousStateName === 'register') {
                //    $state.go('home');
                //} else if ($rootScope.previousStateName === 'register') {
                //} else {
                //    $rootScope.back();
                //}
            }).catch(function () {
                console.log('login Error');
                blockUI.stop();
                $scope.authenticationError = true;
            });
        };
    });
