'use strict';

var socialsFacebookModalAccount = angular.module('socialsFacebookModalAccount', []);

socialsFacebookModalAccount.controller('FacebookModalAccountController',
    ['$scope', '$element', 'username', 'FacebookApi', 'FacebookCredentials', 'close',
        function ($scope, $element, username, FacebookApi, FacebookCredentials, close) {

            FacebookApi.getAccountInfoPromise(username).then(function (accountInfo) {
                $scope.facebookAccountInfo = accountInfo.data;
            });

            $scope.disconnectFromFacebook = function () {
                FacebookCredentials.remove({username: username},
                    function success() {
                        $element.modal('hide');
                        close(true, 500);
                    },
                    function error() {
                        $element.modal('hide');
                        close(false, 500);
                    });
            };
        }]);