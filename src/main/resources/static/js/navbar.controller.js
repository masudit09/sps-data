'use strict';

angular.module('stepApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal) {
        //$scope.isAuthenticated=false;
         $scope.isAuthenticated = Principal.isAuthenticated;
         $scope.$state = $state;

         Principal.identity().then(function (account) {
             $scope.account = account;
         });


         $scope.logout = function () {
             Auth.logout();
             $state.go('home');
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
