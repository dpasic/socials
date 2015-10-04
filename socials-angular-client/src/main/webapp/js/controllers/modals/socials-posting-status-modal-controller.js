'use strict';

var socialsPostingStatusModal = angular.module('socialsPostingStatusModal', []);

socialsPostingStatusModal.controller('PostingStatusModalController', [
    '$scope', '$element', 'username', 'connectedToFacebook', 'connectedToTwitter', 'close', 'Feed',
    function ($scope, $element, username, connectedToFacebook, connectedToTwitter, close, Feed) {

        $scope.inPosting = false;

        $scope.socialNetworksSelection = {
            facebook: false,
            twitter: false,
            instagram: false
        };

        $scope.connectedToFacebook = connectedToFacebook;
        $scope.connectedToTwitter = connectedToTwitter;

        $scope.postStatusesOnNewtworks = function () {
            $scope.inPosting = true;

            var socialsPost = {
                postText: $scope.statusText,
                socialNetworksSelection: $scope.socialNetworksSelection
            };

            Feed.postStatusesPromise(username, JSON.stringify(socialsPost))
                .success(function () {
                    $element.modal('hide');
                    close(true, 500);
                })
                .error(function () {
                    $element.modal('hide');
                    close(false, 500);
                });
        };
    }]);
