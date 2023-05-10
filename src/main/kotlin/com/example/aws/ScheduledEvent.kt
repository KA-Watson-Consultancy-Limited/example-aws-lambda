package com.example.aws

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
//import org.joda.time.DateTime
import java.io.Serializable

/*
* Copyright 2012-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
* the License. A copy of the License is located at
*
* http://aws.amazon.com/apache2.0
*
* or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
* CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
* and limitations under the License.
*/
/**
 * represents a scheduled event
 *
 * needed due to micronaut reflection issue https://github.com/micronaut-projects/micronaut-aws/issues/1611
 */
@Suppress("unused")
@Introspected
@Serdeable
class ScheduledEvent
/**
 * default constructor
 */
    : Serializable, Cloneable {
    /**
     * @return the account id
     */
    /**
     * @param account the account id
     */
    var account: String? = null
    /**
     * @return the aws region
     */
    /**
     * @param region the aws region
     */
    var region: String? = null
    /**
     * @return The details of the events (usually left blank)
     */
    /**
     * @param detail The details of the events (usually left blank)
     */
    var detail: Map<String, Any>? = null
    /**
     * @return The details type - see cloud watch events for more info
     */
    /**
     * @param detailType The details type - see cloud watch events for more info
     */
    var detailType: String? = null
    /**
     * @return the soruce of the event
     */
    /**
     * @param soruce the soruce of the event
     */
    var source: String? = null
    /**
     * @return the id of the event
     */
    /**
     * @param id the id of the event
     */
    var id: String? = null
    /**
     * @return the timestamp for when the event is scheduled
     */
//    /**
//     * @param time the timestamp for when the event is scheduled
//     */
//    var time: DateTime? = null
    /**
     * @return the resources used by event
     */
    /**
     * @param resources the resources used by event
     */
    var resources: List<String>? = null

    /**
     * @param account account id
     * @return ScheduledEvent
     */
    fun withAccount(account: String?): ScheduledEvent {
        this.account = account
        return this
    }

    /**
     * @param region aws region
     * @return ScheduledEvent
     */
    fun withRegion(region: String?): ScheduledEvent {
        this.region = region
        return this
    }

    /**
     * @param detail details of the events (usually left blank)
     * @return ScheduledEvent
     */
    fun withDetail(detail: Map<String, Any>?): ScheduledEvent {
        this.detail = detail
        return this
    }

    /**
     * @param detailType The details type - see cloud watch events for more info
     * @return ScheduledEvent
     */
    fun withDetailType(detailType: String?): ScheduledEvent {
        this.detailType = detailType
        return this
    }

    /**
     * @param source source of the event
     * @return ScheduledEvent
     */
    fun withSource(source: String?): ScheduledEvent {
        this.source = source
        return this
    }

//    /**
//     * @param time the timestamp for when the event is scheduled
//     * @return ScheduledEvent
//     */
//    fun withTime(time: DateTime?): ScheduledEvent {
//        this.time = time
//        return this
//    }

    /**
     * @param id id of event
     * @return ScheduledEvent
     */
    fun withId(id: String?): ScheduledEvent {
        this.id = id
        return this
    }

    /**
     * @param resources list of resource names
     * @return Scheduled event object
     */
    fun withResources(resources: List<String>?): ScheduledEvent {
        this.resources = resources
        return this
    }

    /**
     * Returns a string representation of this object; useful for testing and debugging.
     *
     * @return A string representation of this object.
     *
     * @see Object.toString
     */
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("{")
        if (account != null) sb.append("account: ").append(account).append(",")
        if (region != null) sb.append("region: ").append(region).append(",")
        if (detail != null) sb.append("detail: ").append(detail.toString()).append(",")
        if (detailType != null) sb.append("detailType: ").append(detailType).append(",")
        if (source != null) sb.append("source: ").append(source).append(",")
        if (id != null) sb.append("id: ").append(id).append(",")
//        if (time != null) sb.append("time: ").append(time.toString()).append(",")
        if (resources != null) sb.append("resources: ").append(resources)
        sb.append("}")
        return sb.toString()
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (obj is ScheduledEvent == false) return false
        val other = obj
        if ((other.account == null) xor (account == null)) return false
        if (other.account != null && other.account == account == false) return false
        if ((other.region == null) xor (region == null)) return false
        if (other.region != null && other.region == region == false) return false
        if ((other.detail == null) xor (detail == null)) return false
        if (other.detail != null && other.detail == detail == false) return false
        if ((other.detailType == null) xor (detailType == null)) return false
        if (other.detailType != null && other.detailType == detailType == false) return false
        if ((other.source == null) xor (source == null)) return false
        if (other.source != null && other.source == source == false) return false
        if ((other.id == null) xor (id == null)) return false
        if (other.id != null && other.id == id == false) return false
//        if ((other.time == null) xor (time == null)) return false
//        if (other.time != null && other.time == time == false) return false
        if ((other.resources == null) xor (resources == null)) return false
        return if (other.resources != null && other.resources == resources == false) false else true
    }

    override fun hashCode(): Int {
        val prime = 31
        var hashCode = 1
        hashCode = prime * hashCode + if (account == null) 0 else account.hashCode()
        hashCode = prime * hashCode + if (region == null) 0 else region.hashCode()
        hashCode = prime * hashCode + if (detail == null) 0 else detail.hashCode()
        hashCode = prime * hashCode + if (detailType == null) 0 else detailType.hashCode()
        hashCode = prime * hashCode + if (source == null) 0 else source.hashCode()
        hashCode = prime * hashCode + if (id == null) 0 else id.hashCode()
//        hashCode = prime * hashCode + if (time == null) 0 else time.hashCode()
        hashCode = prime * hashCode + if (resources == null) 0 else resources.hashCode()
        return hashCode
    }

    public override fun clone(): ScheduledEvent {
        return try {
            super.clone() as ScheduledEvent
        } catch (e: CloneNotSupportedException) {
            throw IllegalStateException("Got a CloneNotSupportedException from Object.clone()", e)
        }
    }

    companion object {
        private const val serialVersionUID = -5810383198587331146L
    }
}