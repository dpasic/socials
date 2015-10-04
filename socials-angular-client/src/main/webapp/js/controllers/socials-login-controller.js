'use strict';

var socialsLogin = angular.module('socialsLogin', []);

socialsLogin.controller('LoginController', ['$scope', '$state', 'UserAuth', 'Users', 'UserHelper',
    function ($scope, $state, UserAuth, Users, UserHelper) {
        UserHelper.getStorageSelected(function (user) {
            if (user) {
                $state.go('home.feed');
            }
        });

        $scope.inSigningIn = false;
        $scope.wrongCredentials = false;

        $scope.login = function (credentials) {
            $scope.inSigningIn = true;

            UserAuth.areCredentialsAuthenticatedPromise(credentials).then(function (areAuthenticated) {
                if (!areAuthenticated.data) {
                    $scope.wrongCredentials = true;
                    $scope.credentials.password = '';
                    $scope.inSigningIn = false;
                    return;
                }

                Users.get({username: credentials.username}, function (data) {
                    var user = data.toJSON();
                    UserHelper.setStorageSelected(user);
                    $state.go('home.feed');
                });
            });
        };
    }]);