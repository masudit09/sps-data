var app=angular.module('app', ['LocalStorageModule', 'pascalprecht.translate',
    'ui.bootstrap', // for modal dialogs
    'ngResource', 'ui.router', 'ngCookies','blockUI']);

app.run(function ($rootScope, $location, $window, $http, $state, Auth, Principal) {

    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;
        if (Principal.isIdentityResolved()) {
            Auth.authorize();
        }

    });



    $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
        var titleKey = 'Title' ;

        // Remember previous state unless we've been redirected to login or we've just
        // reset the state memory after logout. If we're redirected to login, our
        // previousState is already set in the authExpiredInterceptor. If we're going
        // to login directly, we don't want to be sent to some previous state anyway
        if (toState.name != 'login' && $rootScope.previousStateName) {
            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;
        }
        // Set the page title key to the one configured in state or use default one

        //updateTitle(titleKey);
        console.log('finish');
        console.log(toState);
        console.log(fromState);
    });

    $rootScope.$on('$stateChangeError',  function(event, toState, toParams, fromState, fromParams,error) {
        console.log(error);
    });

    // if the current translation changes, update the window title
    // $rootScope.$on('$translateChangeSuccess', function() { updateTitle(); });


    $rootScope.back = function() {
        // If previous state is 'activate' or do not exist go to 'home'
        if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
            $state.go('dashboard');
        } else {
            console.log($rootScope.previousStateName)
            console.log($rootScope.previousStateParams)
            if(!$rootScope.previousStateName) {
                $state.go('dashboard');
            }
            if($rootScope.previousStateName && $rootScope.previousStateParams) {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }

        }
    };
    $rootScope.closeAlert = function() {
        $('.message.error, .message.success, .message.warning').hide();
        $rootScope.globalErrorMessage = '';
        $rootScope.globalSuccessMessage = '';
        $rootScope.globalWarningMessage = '';
    }
    $rootScope.setErrorMessage = function (message) {
        $rootScope.closeAlert();
        $('.message.error').show();
        localStorage.setItem('globalErrorMessage', message);
        $rootScope.globalErrorMessage= localStorage.getItem('globalErrorMessage');
        setTimeout(function() { $(".message.error").hide(); }, 2000);
    }
    $rootScope.setFile = function ($file) {
        var  attachment = {};
        try {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    try {
                        attachment.file = base64Data;
                        attachment.fileContentType = $file.type;
                        attachment.fileName = $file.name;
                        attachment.showAddMore = true;
                    } catch (e) {
                        console.log("file upload error.....")
                        console.log(e)
                    }
                };

            }
        } catch (e) {
            console.log("file upload error.....")
            console.log(e)
        }
        return attachment;
    };
    $rootScope.b64toBlob = function b64toBlob(b64Data, contentType, sliceSize) {
        contentType = contentType || '';
        sliceSize = sliceSize || 512;

        var byteCharacters = atob(b64Data);
        var byteArrays = [];

        for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            var slice = byteCharacters.slice(offset, offset + sliceSize);

            var byteNumbers = new Array(slice.length);
            for (var i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }

            var byteArray = new Uint8Array(byteNumbers);

            byteArrays.push(byteArray);
        }

        var blob = new Blob(byteArrays, {type: contentType});
        return blob;
    };

    $rootScope.previewImage = function (content, contentType, name) {
        var blob = $rootScope.b64toBlob(content, contentType);
        $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
        $rootScope.viewerObject.contentType = contentType;
        $rootScope.viewerObject.pageTitle = name;
        // call the modal
        $rootScope.showFilePreviewModal();
    };

    $rootScope.viewerObject = {};
    $rootScope.showFilePreviewModal = function()
    {
        $modal.open({
            templateUrl: '/views/common-file-viewer.html',
            controller: [
                '$scope', '$modalInstance', function($scope, $modalInstance) {
                    $scope.ok = function() {
                        $rootScope.viewerObject = {};
                        $modalInstance.close();
                    };
                    $scope.closeModal = function() {
                        $rootScope.viewerObject = {};
                        $modalInstance.dismiss();
                    };
                }
            ]
        });
    };

    $rootScope.confirmationObject = {};
    $rootScope.showConfirmation = function()
    {
        $modal.open({
            templateUrl: 'static/views/common-confirmation.html',
            controller: [
                '$scope', '$modalInstance', function($scope, $modalInstance) {
                    $scope.ok = function() {
                        $rootScope.confirmationObject = {};
                        $modalInstance.close();
                    };
                    $scope.closeModal = function() {
                        $rootScope.confirmationObject = {};
                        $modalInstance.dismiss();
                    };
                }
            ]
        });
    };

    $rootScope.setSuccessMessage = function (message) {
        $rootScope.closeAlert();
        $('.message.success').show();
        localStorage.setItem('globalSuccessMessage',message);
        $rootScope.globalSuccessMessage= localStorage.getItem('globalSuccessMessage');
        setTimeout(function() { $(".message.success").hide(); }, 2000);
    };

    $rootScope.searchByTrackID = false;

    $rootScope.logout = function () {
        Auth.logout();
        $state.go('home');
    };
})
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider,$translateProvider, $locationProvider) {
        // uncomment below to make alerts look like toast
        //AlertServiceProvider.showAsToast(true);

        //enable CSRF
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';

        //Cache everything except rest api requests
        /*httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*!/, /.*protected.*!/], true);*/

        $urlRouterProvider.otherwise('/');
        $stateProvider.state('site', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: '/views/navbar.html',
                    controller: 'NavbarController'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ]
            }
        });

        $httpProvider.interceptors.push('errorHandlerInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
        /* $httpProvider.interceptors.push('notificationInterceptor');*/

    })
    .config(['$urlMatcherFactoryProvider', function($urlMatcherFactory) {
        $urlMatcherFactory.type('boolean', {
            name : 'boolean',
            decode: function(val) { return val == true ? true : val == "true" ? true : false },
            encode: function(val) { return val ? 1 : 0; },
            equals: function(a, b) { return this.is(a) && a === b; },
            is: function(val) { return [true,false,0,1].indexOf(val) >= 0 },
            pattern: /bool|true|0|1/
        });
    }]);


