---
issue: Implement EntityDamageListener
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Senior Engineer]
model: claude-opus-4-5
blocked-by: [13]
---

# Implement EntityDamageListener

## Context

Prevents PvP damage in protected claims unless attacker is the claim owner. Requires investigation of Hytale damage event system as spike found no explicit documented event.

## Dependencies

- Blocked by 13-protection-manager — Uses ProtectionManager.canDamage() for permission checks

## Acceptance Criteria

- [ ] EntityDamageListener handles player-vs-player damage events
- [ ] Damage cancelled if attacker cannot damage in that claim
- [ ] Claim owner can damage players in their claim (self-defense)
- [ ] Unclaimed areas allow all PvP
- [ ] Attacker receives feedback message when damage prevented
- [ ] Implementation documented if approach differs from expected

## Technical Constraints

- **Spike Finding**: Entity damage API not explicitly documented; KillFeedEvent available but may be post-damage. See [spike-hytale-event-api](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-event-api.md)
- **Fallback**: If no suitable pre-damage event exists, document as known limitation for v0
- **ADR-0002**: Convert victim location to ChunkCoordinate for claim lookup. See [ADR-0002](/.meatware/adr/0002-chunk-coordinate-handling.md)
- **Investigation Required**: Check ECS damage components for interception points

## Workflow

1. Investigate Hytale damage event system (ECS components, events)
2. Identify appropriate event to intercept player damage
3. If suitable event found:
   - Create `EntityDamageListener.java`
   - Extract attacker, victim, and location
   - Check ProtectionManager.canDamage()
   - Cancel if not allowed
4. If no suitable event:
   - Document limitation
   - Create placeholder that logs investigation results
5. Write tests if implementation possible

## Out of Scope

- Mob damage protection
- Environmental damage protection
- Projectile damage (may need separate handling)

## Resources

- [Spike: Hytale Event API](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-event-api.md)
- [ADR-0002: Chunk Coordinate Handling](/.meatware/adr/0002-chunk-coordinate-handling.md)
- [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events) — KillFeedEvent, EntityRemoveEvent (damage events require investigation)
- [HytaleModding.dev - Creating Events](https://hytalemodding.dev/en/docs/guides/plugin/creating-events) — ECS event system patterns
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Entity system tutorials
- [Milestone Analysis](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/milestone-analysis.md) — Accepted risk: Entity damage API uncertainty
