'use strict';

var socialsRegister = angular.module('socialsRegister', []);

socialsRegister.controller('RegisterController', ['$scope', '$state', 'Users', 'UserHelper', 'UsersCredentials',
    function ($scope, $state, Users, UserHelper, UsersCredentials) {

        $scope.inSigningUp = false;
        $scope.registerError = false;

        $scope.registerAndSignIn = function (credentials) {
            $scope.inSigningUp = true;

            var user = {
                username: credentials.username,
                firstName: credentials.firstName,
                lastName: credentials.lastName,
                email: credentials.email
            };

            Users.save({}, user,
                function success() {
                    UsersCredentials.save({username: user.username}, credentials.password,
                        function success() {
                            $scope.$parent.isFirstTimeSignIn = true;
                            UserHelper.setStorageSelected(user);
                            $state.go('home.feed');
                        },
                        function error() {
                            handleError();
                        });
                },
                function error() {
                    handleError();
                });
        };

        function handleError() {
            $scope.registerError = true;
            $scope.credentials.password = '';
            $scope.inSigningUp = false;
        }
    }]);