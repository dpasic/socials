/* Extracted from socials-home-controller.js */

socialsHome.extractTwitterHomeController = function ($scope, URI, TwitterAuth, TwitterCredentials, TwitterApi, ModalService) {
    $scope.connectToTwitter = function () {
        var authWindow = window.open(URI.OPEN_AUTH);
        TwitterAuth.getRequestTokenPromise().then(function (twitterRequestToken) {
            authorizeTwitterInWindow(twitterRequestToken.data, authWindow);
        });
    };

    $scope.openTwitterAccountInfo = function () {
        ModalService.showModal({
            templateUrl: "partials/modals/twitter-account.html",
            controller: "TwitterModalAccountController",
            inputs: {
                username: $scope.user.username
            }
        }).then(function (modal) {
            modal.element.modal();
            modal.close.then(function (areCredentialsDeleted) {
                if (areCredentialsDeleted) {
                    $scope.connectedToTwitter = false;
                    $scope.twitterCredentials = null;
                }
            });
        });
    };

    function authorizeTwitterInWindow(twitterRequestToken, authWindow) {
        authWindow.location = twitterRequestToken.authUrl;

        var pollTimer = window.setInterval(function () {
            try {
                var url = authWindow.document.URL;
                if (url.indexOf(URI.REDIRECT) != -1) {
                    window.clearInterval(pollTimer);
                    var verifier = getUrlQueryParameter(url, 'oauth_verifier');
                    var requestTokenAndVerifier = {
                        requestToken: twitterRequestToken.requestToken,
                        requestTokenSecret: twitterRequestToken.requestTokenSecret,
                        verifier: verifier
                    };

                    TwitterCredentials.save({username: $scope.user.username}, requestTokenAndVerifier,
                        function success(data) {
                            var twitterCredentials = data.toJSON();
                            $scope.connectedToTwitter = true;
                            $scope.twitterCredentials = twitterCredentials;
                            authWindow.close();
                        },
                        function error() {
                            $scope.errorOccurred = true;
                            authWindow.close();
                        });
                }
            } catch (e) {
                // block frame warning
            }
        }, 500);
    }
};