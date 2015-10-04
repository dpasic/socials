'use strict';

var socialsHome = angular.module('socialsHome', []);

socialsHome.controller('HomeController', ['$scope', '$state', 'UserHelper', 'URI', 'TwitterAuth', 'TwitterCredentials', 'TwitterApi',
    'FacebookAuth', 'FacebookCredentials', 'FacebookApi', 'InstagramAuth', 'InstagramCredentials', 'InstagramApi', 'ModalService',
    function ($scope, $state, UserHelper, URI, TwitterAuth, TwitterCredentials, TwitterApi,
              FacebookAuth, FacebookCredentials, FacebookApi, InstagramAuth, InstagramCredentials, InstagramApi, ModalService) {

        UserHelper.getStorageSelected(function (user) {
            if (!user) {
                $state.go('login');
            }
            $scope.user = user;
        });

        $('#firstTimeSignInAlert').on('close.bs.alert', function () {
            $scope.$parent.isFirstTimeSignIn = false;
        });

        $scope.receivedFacebookCredentials = false;
        $scope.receivedTwitterCredentials = false;
        $scope.receivedInstagramCredentials = false;

        $scope.connectedToTwitter = false;
        $scope.connectedToFacebook = false;
        $scope.connectedToInstagram = false;

        FacebookCredentials.get({username: $scope.user.username}, function (data) {
            var facebookCredentials = data.toJSON();
            $scope.receivedFacebookCredentials = true;
            if (Object.keys(facebookCredentials).length == 0) {
                return;
            }

            $scope.connectedToFacebook = true;
            $scope.facebookCredentials = facebookCredentials;
        });

        TwitterCredentials.get({username: $scope.user.username}, function (data) {
            var twitterCredentials = data.toJSON();
            $scope.receivedTwitterCredentials = true;
            if (Object.keys(twitterCredentials).length == 0) {
                return;
            }

            $scope.connectedToTwitter = true;
            $scope.twitterCredentials = twitterCredentials;
        });

        InstagramCredentials.get({username: $scope.user.username}, function (data) {
            var instagramCredentials = data.toJSON();
            $scope.receivedInstagramCredentials = true;
            if (Object.keys(instagramCredentials).length == 0) {
                return;
            }

            $scope.connectedToInstagram = true;
            $scope.instagramCredentials = instagramCredentials;
        });

        $scope.logout = function () {
            UserHelper.clearStorageSelected();
            $state.go('login');
        };

        socialsHome.extractTwitterHomeController($scope, URI, TwitterAuth, TwitterCredentials, TwitterApi, ModalService);
        socialsHome.extractFacebookHomeController($scope, URI, FacebookAuth, FacebookCredentials, FacebookApi, ModalService);
        socialsHome.extractInstagramHomeController($scope, URI, InstagramAuth, InstagramCredentials, InstagramApi, ModalService);
    }]);
