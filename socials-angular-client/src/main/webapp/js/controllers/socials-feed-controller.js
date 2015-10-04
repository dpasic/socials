'use strict';

var socialsFeed = angular.module('socialsFeed', ['ngLocalize']);

socialsFeed.controller('FeedController', ['$scope', '$interval', '$timeout', 'ModalService', 'Feed',
    function ($scope, $interval, $timeout, ModalService, Feed) {
        $scope.errorPost = false;
        $scope.finishedInitFeed = false;
        $scope.newStatusArrivedHeader = false;
        $scope.newStatusArrived = false;

        var REFRESH_FEED_INTERVAL = 6000;
        var TIMEOUT = 3000;

        $(window).scroll(function () {
            if ($(this).scrollTop() != 0) {
                $('#toTop').fadeIn();
            } else {
                $('#toTop').fadeOut();
                $scope.newStatusArrived = false;
            }
        });
        $('#toTop').click(function () {
            $("html, body").animate({scrollTop: 0}, 600);
            return false;
        });

        $scope.notifyNewStatus = function (element) {
            if (element.scrollTop() != 0) {
                $scope.newStatusArrived = true;
            }
            $scope.newStatusArrivedHeader = true;
            $timeout(function () {
                $scope.newStatusArrivedHeader = false;
            }, TIMEOUT);
        };

        $scope.fillFeedWithRefreshFlag = function (isRefresh) {
            var socialNetworkList = [];
            if ($scope.connectedToFacebook) {
                socialNetworkList.push('facebook');
            }
            if ($scope.connectedToTwitter) {
                socialNetworkList.push('twitter');
            }
            if ($scope.connectedToInstagram) {
                socialNetworkList.push('instagram');
            }

            Feed.getFeedPromise($scope.user.username, socialNetworkList).then(function (feed) {
                if (!isRefresh) {
                    $scope.feed = feed.data;
                    return;
                }
                var newFeed = feed.data;
                if ($scope.feed.length == 0 && newFeed.length > 0) {
                    $scope.notifyNewStatus($(this));
                } else if ($scope.feed.length > 0 && newFeed.length > 0) {
                    var firstOldStatus = $scope.feed[0];
                    var firstNewStatus = newFeed[0];
                    if (firstOldStatus.createdAt < firstNewStatus.createdAt) {
                        $scope.notifyNewStatus($(this));
                    }
                }
                $scope.feed = newFeed;
            });
        };

        $scope.$watch('receivedFacebookCredentials && receivedTwitterCredentials && receivedInstagramCredentials', function() {
            if ($scope.receivedFacebookCredentials && $scope.receivedTwitterCredentials && $scope.receivedInstagramCredentials) {
                $scope.fillFeedWithRefreshFlag(false);
                $scope.finishedInitFeed = true;
            }
        });

        $scope.$watch('finishedInitFeed', function() {
            if ($scope.finishedInitFeed) {
                refreshingFeedPollTimer = $interval(function () {
                    if ($scope.finishedInitFeed) {
                        $scope.fillFeedWithRefreshFlag(true);
                    }
                }, REFRESH_FEED_INTERVAL);
            }
        });

        $scope.openPostingStatus = function () {
            ModalService.showModal({
                templateUrl: "partials/modals/posting-status.html",
                controller: "PostingStatusModalController",
                inputs: {
                    username: $scope.user.username,
                    connectedToFacebook: $scope.connectedToFacebook,
                    connectedToTwitter: $scope.connectedToTwitter
                }
            }).then(function (modal) {
                modal.element.modal();
                modal.close.then(function (isPosted) {
                    if (!isPosted) {
                        $scope.errorPost = true;
                    }
                });
            });
        };
    }]);