'use strict';

angular.module('lolcoachApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
