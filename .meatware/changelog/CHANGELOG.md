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

---
