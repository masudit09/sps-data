'use strict';

angular.module('app')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal) {
        //$scope.isAuthenticated=false;
         $scope.isAuthenticated = Principal.isAuthenticated;
         $scope.$state = $state;

         Principal.identity().then(function (account) {
             $scope.account = account;
             console.log(account);
             if(account){

                 $scope.username=account.username;
             }
            // $scope.userName= account.userName;
         });
        $scope.username='Account';

        $scope.$on("updateUserName", function () {
            Principal.identity().then(function (account) {
                $scope.account = account;
                console.log(account);
                if (account) {

                    $scope.username = account.username;
                }
                // $scope.userName= account.userName;
            });
            // if(!$scope.notificationRunniong) {
            //     $rootScope.notificationRunniong = true;
            //     $scope.notificationRunniong=true;
            //     $scope.reload();
            // }
        });


         $scope.logout = function () {
             Auth.logout();
             $scope.username='Account';
             $state.go('login');
         };
         $scope.submit = function () {
             var form = $scope.form;
             $scope.submitted = true;
             if (form.$valid) {
                 var searchData = {'q': $scope.q};
                 $state.go('search', searchData);
             }
         };
    });
