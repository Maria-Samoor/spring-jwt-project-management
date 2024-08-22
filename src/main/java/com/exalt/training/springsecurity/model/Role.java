package com.exalt.training.springsecurity.model;
/**
 * Enumeration representing the different roles in the system.
 * Each role defines a level of authority that a user can have.
 *
 * <ul>
 *     <li>CEO: Represents the highest authority in the system.</li>
 *     <li>TeamLeader: Represents a leadership role with moderate authority.</li>
 *     <li>TeamMember: Represents a basic role with standard authority.</li>
 * </ul>
 */
public enum Role {
    CEO, //highest authority level
    TeamLeader, //moderate authority level
    TeamMember //basic authority level
}
