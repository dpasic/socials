/* Extracted from socials-home-controller.js */

socialsHome.extractInstagramHomeController = function ($scope, URI, InstagramAuth, InstagramCredentials, InstagramApi, ModalService) {
    $scope.connectToInstagram = function () {
        var authWindow = window.open(URI.OPEN_AUTH);
        InstagramAuth.getAuthUrlPromise().then(function (authUrl) {
            authorizeInstagramInWindow(authUrl.data, authWindow);
        });
    };

    $scope.openInstagramAccountInfo = function () {
        ModalService.showModal({
            templateUrl: "partials/modals/instagram-account.html",
            controller: "InstagramModalAccountController",
            inputs: {
                username: $scope.user.username
            }
        }).then(function (modal) {
            modal.element.modal();
            modal.close.then(function (areCredentialsDeleted) {
                if (areCredentialsDeleted) {
                    $scope.connectedToInstagram = false;
                    $scope.instagramCredentials = null;
                }
            });
        });
    };

    function authorizeInstagramInWindow(authUrl, authWindow) {
        authWindow.location = authUrl;

        var pollTimer = window.setInterval(function () {
            try {
                var url = authWindow.document.URL;
                if (url.indexOf(URI.REDIRECT) != -1) {
                    window.clearInterval(pollTimer);
                    var code = getUrlQueryParameter(url, 'code');

                    InstagramCredentials.save({username: $scope.user.username}, code,
                        function success(data) {
                            var instagramCredentials = data.toJSON();
                            $scope.connectedToInstagram = true;
                            $scope.instagramCredentials = instagramCredentials;
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