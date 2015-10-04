'use strict';

var socialsInstagramModalAccount = angular.module('socialsInstagramModalAccount', []);

socialsInstagramModalAccount.controller('InstagramModalAccountController',
    ['$scope', '$element', 'username', 'InstagramApi', 'InstagramCredentials', 'close',
        function ($scope, $element, username, InstagramApi, InstagramCredentials, close) {

            InstagramApi.getAccountInfoPromise(username).then(function (accountInfo) {
                $scope.instagramAccountInfo = accountInfo.data;
            });

            $scope.disconnectFromInstagram = function () {
                InstagramCredentials.remove({username: username},
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