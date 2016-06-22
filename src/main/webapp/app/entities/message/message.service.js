(function() {
    'use strict';
    angular
        .module('fleetApp')
        .factory('Message', Message);

    Message.$inject = ['$resource', 'DateUtils'];

    function Message ($resource, DateUtils) {
        var resourceUrl =  'api/messages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'POST', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.messageTime = DateUtils.convertDateTimeFromServer(data.messageTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
