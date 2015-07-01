'use strict';

angular.module('lolcoachApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


