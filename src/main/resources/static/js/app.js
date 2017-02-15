var app=angular.module('app', ['LocalStorageModule', 'pascalprecht.translate',
    'ui.bootstrap', // for modal dialogs
    'ngResource', 'ui.router', 'ngCookies']);

app.run(function ($rootScope, $location, $window, $http, $state, Auth, Principal) {

        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;

            console.log('called');
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
                $state.go('home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
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


