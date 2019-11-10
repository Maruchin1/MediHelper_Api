package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DaysOfWeekDto(
        @JsonProperty(value = "monday")
        var monday: Boolean,

        @JsonProperty(value = "tuesday")
        var tuesday: Boolean,

        @JsonProperty(value = "wednesday")
        var wednesday: Boolean,

        @JsonProperty(value = "thursday")
        var thursday: Boolean,

        @JsonProperty(value = "friday")
        var friday: Boolean,

        @JsonProperty(value = "saturday")
        var saturday: Boolean,

        @JsonProperty(value = "sunday")
        var sunday: Boolean
)