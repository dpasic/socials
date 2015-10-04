/* Extracted from socials-home-controller.js */

socialsHome.extractFacebookHomeController = function ($scope, URI, FacebookAuth, FacebookCredentials, FacebookApi, ModalService) {
    $scope.connectToFacebook = function () {
        var authWindow = window.open(URI.OPEN_AUTH);
        FacebookAuth.getAuthUrlPromise().then(function (authUrl) {
            authorizeFacebookInWindow(authUrl.data, authWindow);
        });
    };

    $scope.openFacebookAccountInfo = function () {
        ModalService.showModal({
            templateUrl: "partials/modals/facebook-account.html",
            controller: "FacebookModalAccountController",
            inputs: {
                username: $scope.user.username
            }
        }).then(function (modal) {
            modal.element.modal();
            modal.close.then(function (areCredentialsDeleted) {
                if (areCredentialsDeleted) {
                    $scope.connectedToFacebook = false;
                    $scope.facebookCredentials = null;
                }
            });
        });
    };

    function authorizeFacebookInWindow(authUrl, authWindow) {
        authWindow.location = authUrl;

        var pollTimer = window.setInterval(function () {
            try {
                var url = authWindow.document.URL;
                if (url.indexOf(URI.REDIRECT) != -1) {
                    window.clearInterval(pollTimer);
                    var code = getUrlQueryParameter(url, 'code');

                    FacebookCredentials.save({username: $scope.user.username}, code,
                        function success(data) {
                            var facebookCredentials = data.toJSON();
                            $scope.connectedToFacebook = true;
                            $scope.facebookCredentials = facebookCredentials;
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