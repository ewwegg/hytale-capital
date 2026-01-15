# Changelog

All notable changes to this project will be documented in this file.

The format is based on:

- [Keep a Changelog v1.1.0](https://keepachangelog.com/en/1.1.0/) — Changelog format standard
- [Semantic Versioning v2.0.0](https://semver.org/spec/v2.0.0.html) — Version numbering specification

## How to Use This Changelog

- Each commit generates a detailed changelog entry in monthly folders (format: `YYMM-changelog/`)
- Individual changelog files are named: `YYMMDDHHMM-description.md`
- This index file links to all detailed changelog entries
- Entries are organized in reverse chronological order (newest first)
- Breaking changes are marked with **[BREAKING]** tag
- Sections included vary by commit type (see Dynamic Changelog Template in `/git:commit`)

## Entry Format

```
[TIMESTAMP-description](./YYMM-changelog/TIMESTAMP-description.md)
  - **Git Comment**: conventional commit message
  - **[Primary Section]**: 8-12 word summary of changes
```

Technical Debt line included only for `feat`, `fix`, `refactor`, and `perf` commits.

---

## [Unreleased]

- [2601152322-initialize-project-foundation](./2601-changelog/2601152322-initialize-project-foundation.md)
  - **Git Comment**: feat(foundation): initialize project structure and build system
  - **Added**: Gradle multi-module project, plugin foundation, API stubs, milestone structure
  - **Technical Debt**: Version adjustments from spec, Hytale API stubs pending official SDK

- [2601151952-enhance-issue-documentation-resources](./2601-changelog/2601151952-enhance-issue-documentation-resources.md)
  - **Git Comment**: docs(roadmap): enhance issue documentation with resources and fix paths
  - **Changed**: Updated paths and added resource links across 21 core foundation issues

- [2601151931-fix-absolute-paths-issue-links](./2601-changelog/2601151931-fix-absolute-paths-issue-links.md)
  - **Git Comment**: docs(roadmap): fix absolute paths in issue markdown links
  - **Changed**: Updated cross-reference links in issue files to use absolute paths

- [2601151910-add-adrs-milestone-artifacts](./2601-changelog/2601151910-add-adrs-milestone-artifacts.md)
  - **Git Comment**: docs(planning): add adrs and milestone artifacts for core foundation
  - **Changed**: Added project guidance, 4 ADRs, and Core Foundation milestone artifacts

---
