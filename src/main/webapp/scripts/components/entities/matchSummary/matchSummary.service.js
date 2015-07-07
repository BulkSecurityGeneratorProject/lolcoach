'use strict';

angular.module('lolcoachApp')
    .factory('MatchSummary', function ($resource, DateUtils) {
        return $resource('api/matchSummarys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
