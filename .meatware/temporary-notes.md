# Temporary Notes

Do not index or include in any project documentation (including changelog).

## Important

claude --dangerously-skip-permissions

## To Do

1. Create "how to" doc, explaining process of slash commands so it's easy to follow and can be made simpler in future.
2. Update all milestone / issue commands to use issue title, or an internal issue referencing system like 001-01-3, so that once issues are in github it's more
3. Connect tech stack MCPs for use in planning. supabase, nextjs16 etc.
4. Make sure stucture of dependencies in the issues is consistant and there's no hangover references, example of problem:
   "
   Dependencies: Blocked by #3, #4

   Blocked by 2-create-supabase-project — Need Supabase project for storage bucket
   Blocked by 3-install-configure-payload-cms — Need Payload installed to configure adapter
   "

5. meatware:issue-ready - make a subagent go and find the issue information and pass it back to the orchestrator to save context window, and also use a lower model haiku

## Miscellaneous Notes
