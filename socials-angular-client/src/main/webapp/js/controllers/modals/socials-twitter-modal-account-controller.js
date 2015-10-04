'use strict';

var socialsTwitterModalAccount = angular.module('socialsTwitterModalAccount', []);

socialsTwitterModalAccount.controller('TwitterModalAccountController',
    ['$scope', '$element', 'username', 'TwitterApi', 'TwitterCredentials', 'close',
        function ($scope, $element, username, TwitterApi, TwitterCredentials, close) {

            TwitterApi.getAccountInfoPromise(username).then(function (accountInfo) {
                $scope.twitterAccountInfo = accountInfo.data;
            });

            $scope.disconnectFromTwitter = function () {
                TwitterCredentials.remove({username: username},
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