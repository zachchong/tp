---
layout: default.md
title: User Guide
pageNav: 3
---

<style>
details summary { cursor: pointer; list-style: none; }
details summary h2 { display: inline; margin: 0; }

details summary h2::after {
  content: " (click to expand)";
  font-weight: 400;
  font-size: 0.95rem;
  color: #6b7280;
  margin-left: .15rem;
}

details[open] summary h2::after {
  content: " (click to collapse)";
}

details table { margin-top: .5rem; }
</style>

<style>
.nav-tabs { 
  border-bottom: 2px solid #e5e7eb;
  margin-top: .5rem;
}
.nav-tabs .nav-link {
  padding: .5rem .9rem;
  font-weight: 600;
  color: #374151;
  border: 1px solid transparent;
  border-radius: .6rem .6rem 0 0;
}
.nav-tabs .nav-link:hover {
  color: #111827; 
  background: #ffecb8;
}
.nav-tabs .nav-link.active {
  color: #111827;
  background: #ffcb3b;
  border-color: #e5e7eb #e5e7eb #ffcb3b;
}

.tab-content {
  border: 1px solid #ffcb3b;
  border-top: none;
  border-radius: 0 .6rem .6rem .6rem;
  padding: 1rem;
  background: #fff9e8;
  box-shadow: 0 1px 0 rgba(0,0,0,.02);
}
</style>

<script>
(function () {
  const idToTab = {
    'general-parameters': 'General',
    'contact-parameters': 'Contact',
    'interview-parameters': 'Interview',
    'faq-general-qa': 'General Q&A',
    'faq-common-problems': 'Common Problems & Fixes',
    'faq-troubleshooting': 'Troubleshooting',
    'faq-feature-rationale': 'Feature Rationale'
  };

  // make matching resilient to emojis/extra spaces
  const norm = s => (s || '').toLowerCase().replace(/[^a-z0-9]+/g, '');

  function activateTabFor(id) {
    const wanted = idToTab[id];
    if (!wanted) return;
    const tabs = Array.from(document.querySelectorAll('.nav-tabs .nav-link'));
    const target = tabs.find(a => norm(a.textContent).includes(norm(wanted)));
    if (target && !target.classList.contains('active')) target.click();
  }

  function handleHash() {
    const id = (location.hash || '').slice(1);
    if (id) activateTabFor(id);
  }

  // Switch on TOC click (instant) and on hashchange (fallback)
  document.addEventListener('click', (e) => {
    const a = e.target.closest('a[href^="#"]');
    if (!a) return;
    const id = a.getAttribute('href').slice(1);
    activateTabFor(id);
  });

  window.addEventListener('hashchange', handleHash);
  document.addEventListener('DOMContentLoaded', handleHash);
})();
</script>

<style>
  .visually-hidden-anchor {
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  padding: 0;
  overflow: hidden;
  clip: rect(0 0 0 0);
  clip-path: inset(50%);
  border: 0;
}

html { scroll-behavior: smooth; }

#general-parameters,
#contact-parameters,
#interview-parameters {
  scroll-margin-top: 80px;
}
</style>

<style>
/* Roadmap grid + cards */
.roadmap-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: .9rem;
  margin-top: .75rem;
}
.roadmap-card {
  background: #fffdf5;
  border: 1px solid #ffe082;
  border-radius: .75rem;
  padding: 1rem;
  box-shadow: 0 1px 0 rgba(0,0,0,.03);
}
.roadmap-card h4 {
  display: flex; align-items: center; gap: .5rem;
  margin: 0 0 .4rem 0;
  font-size: 1.05rem;
}
.roadmap-meta {
  display: flex; flex-wrap: wrap; gap: .5rem; margin: .4rem 0 .6rem 0;
  font-size: .85rem; color: #4b5563;
}
.chip {
  display: inline-block;
  padding: .15rem .5rem;
  border-radius: 999px;
  font-weight: 600;
  font-size: .75rem;
  border: 1px solid;
}
.chip.planned    { color:#8a6d00; background:#fff7cc; border-color:#ffe082; }
.chip.proposed  { color:#1f2937; background:#e5e7eb; border-color:#e5e7eb; }

.why, .you-can {
  margin: .5rem 0 0 0; padding-left: 1.1rem;
}
.roadmap-actions {
  margin-top: .7rem; display:flex; gap:.5rem; flex-wrap:wrap;
}
.btn-ghost {
  border:1px dashed #ffcb3b; color:#7c5b00; background:#fffaf0;
  padding:.3rem .6rem; border-radius:.6rem; font-size:.85rem; text-decoration:none;
}
</style>

<style>
/* Accordion cheat-sheet */
.rule-accordion { border:1px solid #ffcb3b; border-radius:.75rem; overflow:hidden; background:#fff9e8; }
.rule-accordion details { border-top:1px solid #ffe28a; }
.rule-accordion details:first-of-type { border-top:none; }
.rule-accordion summary {
  list-style:none; cursor:pointer; padding:.8rem 1rem; font-weight:700; color:#7c5b00;
  display:flex; align-items:center; gap:.6rem; flex-wrap: wrap;
}
.rule-accordion summary::-webkit-details-marker { display:none; }
.rule-accordion summary .caret { transition:transform .2s ease; flex: 0 0 auto; }
.rule-accordion details[open] summary .caret { transform:rotate(90deg); }
.rule-accordion .rule-body { padding:.8rem 1rem 1rem 2.2rem; color:#374151; }

.rule-accordion .example {
  background:#fffdf5; border:1px dashed #ffcb3b; border-radius:.6rem; padding:.6rem .7rem; margin:.5rem 0 0;
}
.rule-accordion pre { margin:.35rem 0 0; }
.rule-accordion code { font-family:ui-monospace, SFMono-Regular, Menlo, Consolas, "Liberation Mono", monospace; }

.copybtn {
  float:right; font-size:.75rem; padding:.15rem .45rem; border:1px solid #ffcb3b; background:#fff; border-radius:.4rem;
  cursor:pointer;
}
.copybtn:hover { background:#fff7cc; }
</style>

<script>
/* optional: small copy buttons for examples */
document.addEventListener('click', (e) => {
  const btn = e.target.closest('.copybtn');
  if (!btn) return;
  const text = btn.getAttribute('data-copy');
  navigator.clipboard.writeText(text || '').then(() => {
    const old = btn.textContent; btn.textContent = 'Copied!';
    setTimeout(() => btn.textContent = old, 900);
  });
});
</script>

<style>
details.caution {
  border: 1px solid #ffcb3b;
  background: #fff9e8;
  border-radius: .6rem;
  margin: 1rem 0;
}
details.caution summary {
  cursor: pointer;
  list-style: none;
  padding: .65rem .8rem;
  font-weight: 700;
  color: #7c5b00;
  display: flex; align-items: center; gap: .5rem;
}
details.caution summary::-webkit-details-marker { display: none; }
details.caution .caution-body {
  padding: .2rem .9rem .8rem .9rem;
  color: #374151;
}
</style>

<style>
/* Make all H2 big & dark */
h2 {
  color: #0C3878 !important;
  font-size: 2.4rem !important;
  font-weight: 800 !important;
  line-height: 1.2;
  margin-top: 2rem;
  margin-bottom: .9rem;
}
</style>

<style>
h1 {
  color: #E84C23 !important;
  font-size: 3.2rem !important;
  font-weight: 800 !important;
  line-height: 1.2;
  margin-top: 2rem;
  margin-bottom: .9rem;
}
</style>

<style>
details summary code {
  display: inline;
  white-space: normal;
  overflow-wrap: anywhere;
  word-break: break-word;
  margin: 0;
  padding: .05em .25em;
  background: rgba(0,0,0,.03);
  border-radius: .25em;
  font-family: ui-monospace, SFMono-Regular, Menlo, Consolas, "Liberation Mono", monospace;
}
</style>

![PressPalAbout](images/PressPalAbout.png)

# User Guide

## About

PressPal is designed to streamline contact and interview management for breaking news reporters. Traditional address books and note-taking tools are not optimized for the high-speed, high-pressure environment of news reporting, where reporters must manage hundreds of contacts across multiple sources (phones, emails, notebooks).

The **goal of PressPal** is to:
- Provide reporters with a single command-driven system for organizing and retrieving contacts quickly.
- Enable fast entry and retrieval of context (interview history, organisation, role, notes) while working on multiple stories simultaneously.
- Support the entire lifecycle of a contact — from initial outreach, to active follow-up, to archiving once a story concludes.
  Ultimately, PressPal aims to reduce cognitive load for reporters, allowing them to focus on storytelling while ensuring no lead, contact, or follow-up is lost.

## Table of Contents
1. [Quick Start](#quick-start)
2. [Input Parameters](#input-parameters)
    - [General Parameters](#general-parameters)
    - [Contact Parameters](#contact-parameters)
    - [Interview Parameters](#interview-parameters)
    - [Saving the data](#saving-the-data)
3. [Features](#features)
    - **General**
      - [Viewing help : `help`](#viewing-help-help)
      - [Clearing all entries : `clear`](#clearing-all-entries-clear)
      - [Exiting the program : `exit`](#exiting-the-program-exit)
    - **Contact Management Commands**
      - [Adding a person: `add`](#adding-a-person-add)
      - [Deleting a person : `delete`](#deleting-a-person-delete)
      - [Editing a person : `edit`](#editing-a-person-edit)
      - [Listing all persons : `list`](#listing-all-persons-list)
      - [Locating persons by name, organisation, role, or categories : `find`](#locating-persons-by-name-organisation-role-or-categories-find)
    - **Interview Management Commands**
      - [Adding an interview to a contact : `addInterview`](#adding-an-interview-to-a-contact-addinterview)
      - [Deleting an interview from a contact : `deleteInterview`](#deleting-an-interview-from-a-contact-deleteinterview)
      - [Listing all interviews of a contact : `listInterview`](#listing-all-interviews-of-a-contact-listinterview)
      - [Display the upcoming interview : `nextInterview`](#display-the-upcoming-interview-nextinterview)
    - **Category Management Commands**
      - [Add category(s) to a person : `addCat`](#add-categorys-to-a-person-addcat)
      - [Delete category(s) from a person : `deleteCat`](#delete-categorys-from-a-person-deletecat)
4. [Frequently Asked Questions (FAQ)](#frequently-asked-questions-faq)
    - [General Q&A](#faq-general-qa)
    - [Common Problems & Fixes](#faq-common-problems)
    - [Troubleshooting](#faq-troubleshooting)
    - [Feature Rationale](#faq-feature-rationale)
7. [Future Iteration Plans](#future-iteration-plans)
8. [Command summary](#command-summary)

### User Profiles
- Users with basic familiarity with Windows/macOS/Linux file navigation and opening a terminal.
- Users who favor CLI over GUI and type quickly.
- Users who manage an extensive network of contacts and numerous scheduled interviews to monitor.

### System Requirements
- Java 17 (or newer) installed and available on the PATH.
- Operating System: Windows, macOS, or Linux.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Download and install `Java 17` on your computer.<br>
   - **Windows & Linux Users:** Download and install Java from [Oracle JDK 17 Archive Downloads](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
     - **Windows Users:** Follow the instructions [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html) to set up and verify.
   - **Mac users:** Follow the instructions [here](https://se-education.org/guides/tutorials/javaInstallationMac.html) to set up and verify.<br/>

<panel header=":bulb: Tip: To verify Java 17 installation" type="info" expanded>

**Windows:** Open **Command Prompt** or **PowerShell**.  
**macOS:** Open **Terminal**.  
**Linux:** Open your **terminal**.

Then run: `java -version`

**You should see a version that starts with `17`.**  
Examples:
- `openjdk version "17.0.x" ...`
- `java version "17.0.x" ...`
- `Eclipse Temurin 17.0.x`

If it doesn’t start with `17` (e.g., `1.8`, `11`) or says `command not found`, Java 17 isn’t active.  
Please refer to the instructions in **Step 1 (Quick Start)** to set it up correctly.

</panel>

2. **Download our app:**
   - Install the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-W08-1/tp/releases/latest).<br/><br/>

3. **Prepare a folder:**
   - Copy the file to the folder you want to use as the _home folder_ for PressPal.<br/><br/>

4. **Launch the app:** 
   - Open a terminal
   - Navigate to the folder where you copied the `.jar` file to. 
     - For **Windows Users**:
       - Open the Command Prompt and type `cd` followed by the folder path, then press Enter.
     - For **macOS/Linux Users**:
       - Open a terminal and type `cd` followed by the folder path, then press Enter.
   - Launch the app by entering the following command:<br/>
     `java -jar PressPal.jar`
   - A GUI like the one below should appear with some sample data:
     ![Ui](images/Ui.png)<br/><br/>

5. **Using the app:** Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Here are some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com r/Student o/NUS c/friends c/owesMoney` : Adds a contact named `John Doe` to the Contact Book.

   * `delete i/3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. **Learning more about the App:**
   * Refer to the [Features](#features) below for more details about the capabilities of PressPal.<br/><br/>

--------------------------------------------------------------------------------------------------------------------

<details open>
  <summary><h2>Input Format & Parameters</h2></summary>

<details class="caution" open>
  <summary>⚠️ CAUTION: For <u>users using the PDF version</u> of the user guide, please read! </summary>
  <div class="caution-body">
    - Since the PDF automatically expands all the tabs, the <strong>"click to collapse" feature will not work!</strong><br/>
    - <strong>Copy buttons will not work</strong> on the PDF version!
  </div>
</details>

<panel header=":information_source: Notes about parameters" type="info" expanded>

- All parameters will be in the form of `p/PARAMETER` where `p/` is the symbol designated for that parameter.
  - For Example, the command `listInterview i/1`
    - `i/` is the parameter symbol.
    - `1` is the parameter.
<br/><br/>
- Words in `UPPER_CASE` are the parameters to be supplied by the user.
  - For Example, the command `add n/NAME`
    - `NAME` is a parameter which can be used as `add n/John Doe`.

</panel>

<panel header=":memo: Notes about the command format" type="info" expanded>

<div class="rule-accordion">
  <details>
    <summary><span class="caret">▶</span> Items in square brackets are optional</summary>
    <div class="rule-body">
      Use optional flags when you need them.
      <div class="example">
        <button class="copybtn" data-copy="add n/Bobby Boon o/NUS r/Student p/12345678">Copy</button>
        <strong>Example 1A (<code>add</code> command):</strong><br/>
        <code>add n/Bobby Boon o/NUS r/Student p/12345678</code><br/>
        → <code>CATEGORY</code> (<code>c/</code>) can be omitted here because it is <strong>optional</strong>.
      </div>
      <div class="example">
        <button class="copybtn" data-copy="add n/Bobby Boon o/NUS r/Student p/12345678 c/friends">Copy</button>
        <strong>Example 1B (<code>add</code> command):</strong><br/>
        <code>add n/Bobby Boon o/NUS r/Student p/12345678 c/friends</code><br/>
        → The category "friends" is added with the contact here, even though it can be omitted too.
      </div>
    </div>
  </details>

  <details>
  <summary>
    <span class="caret">▶</span>
    <span class="summary-text">
      The only parameter symbol that can be repeated is <code>CATEGORY</code> (<code>/c</code>).
    </span>
  </summary>
    <div class="rule-body">
      You may add as many parameters of <code>CATEGORY</code> (<code>/c</code>), but there should be minimally one parameter specified.
      <div class="example">
        <button class="copybtn" data-copy="addCat i/1 c/Elections c/ProtestA">Copy</button>
        <strong>Example 1 (<code>addCat</code> command):</strong><br/>
        <code>addCat i/1 c/Elections c/ProtestA</code><br/>
        → Add "Elections" and "ProtestA" categories (if they exist) to the person at index 1 of the currently displayed list.
      </div>
     <div class="example">
        <button class="copybtn" data-copy="deleteCat i/1 c/Friends c/Elections">Copy</button>
        <strong>Example 2 (<code>deleteCat</code> command):</strong><br/>
        <code>deleteCat i/1 c/Friends c/Elections</code><br/>
        → Remove "Friends" and "Elections" categories (if they exist) from the person at index 1 of the currently displayed list.
      </div>
    </div>
  </details>

  <details>
    <summary><span class="caret">▶</span> Parameters can be in any order.</summary>
    <div class="rule-body">
      Order doesn’t matter unless stated otherwise.
      <div class="example">
        <button class="copybtn" data-copy="add p/12345678 n/Bobby Boon o/NUS r/Student">Copy</button>
        <strong>Example 1A (<code>add</code> command):</strong><br/>
        <code>add p/12345678 n/Bobby Boon o/NUS r/Student</code><br/>
        ↔ Is <strong>identical</strong> to Example 1B.
      </div>
      <div class="example">
        <button class="copybtn" data-copy="add r/Student o/NUS n/Bobby Boon p/12345678">Copy</button>
        <strong>Example 1B (<code>add</code> command):</strong><br/>
        <code>add r/Student o/NUS n/Bobby Boon p/12345678</code><br/>
        ↔ Is <strong>identical</strong> to Example 1A.
      </div>
    </div>
  </details>

  <details>
    <summary><span class="caret">▶</span> Extra parameters on commands that don't take arguments are ignored.</summary>
    <div class="rule-body">
      Commands like <code>help</code>, <code>list</code>, <code>exit</code>, <code>clear</code> ignore extras.
      <div class="example">
        <button class="copybtn" data-copy="help 123">Copy</button>
        <strong>Example:</strong><br/>
        <code>help 123</code><br/>
        ↔ Is <strong>treated the same</strong> as <code>help</code>.
      </div>
    </div>
  </details>

</div>
</panel>

<h3 id="general-parameters" class="visually-hidden-anchor">General Parameters</h3>
<h3 id="contact-parameters" class="visually-hidden-anchor">Contact Parameters</h3>
<h3 id="interview-parameters" class="visually-hidden-anchor">Interview Parameters</h3>

<details class="caution" open>
  <summary>⚠️ NOTE: Please carefully go through the table below! </summary>
  <div class="caution-body">
    The table below will detail the acceptable format of each parameter, which applies to all command types.
  </div>
</details>

<tabs>

  <tab header="🧭 **General**" active>
  <h3>General Parameters</h3>

| Symbol | Parameter        | Description                                   | Constraints                                                                                                                                                                                                                                                                                                                                                                                                             |
|:------:|:-----------------|:----------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  `i/`  | `PERSON_INDEX`   | Index of a person in the **current** list.    | **Positive integer** (1, 2, …); must exist in the current displayed list.<br/><br/> ✅ **Valid** (when list has ≥2 contacts):<br/>- `i/1`<br/>- `i/ 02` (space inside and leading zero)<br/><br/>**❌ Invalid:**<br/>- `i/0` (zero)<br/>- `i/-1` (negative)<br/>- `i/2.5` (decimal)<br/>- `i/abc` (non-numeric)<br/>- `i/999` (out of range, when list has <999 contacts)                                                 |
|  `v/`  | `INTERVIEW_INDEX` | Index of an interview for the selected person.| **Positive integer** (1, 2, …); must exist in the current displayed list.<br/><br/> ✅ **Valid** (when the person has ≥2 interviews):<br/>- `v/1`<br/>- `i/ 02` (space inside and leading zero)<br/><br/> **❌ Invalid:**<br/>- `v/0` (zero)<br/>- `v/-1` (negative)<br/>- `v/1.5` (decimal)<br/>- `v/abc` (non-numeric)<br/>- `v/999` (out of range for that person)<br/>- `v/1` (when the person has **no interviews**) |
|   NA   | `KEYWORD`        | One or more words used for searching.         | Non-empty string.                                                                                                                                                                                                                                                                                                                                                                                                       |


  </tab>
  <tab header="👤 **Contact**">
  <h3>Contact Parameters</h3>

| Symbol | Parameter      | Description                                       | Constraints                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|:-----:|:---------------|:--------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `n/`  | `NAME`         | Person’s full name as you’d like it displayed.    | Alphanumeric and spaces; **first character must be alphanumeric**; cannot be blank. Multiple spaces allowed.<br/><br/>✅ **Valid:**<br/>- `n/John Doe` (extra spacing is allowed between words, not shown here)<br/><br/>❌ **Invalid:**<br/>- `n/` (empty string)<br/>- `n/Arun Kumar s/o Ramesh Kumar` (special characters like `&`, `'`, `/` not allowed)<br/>- `n/ஆர்த்தி` (non-english name)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| `o/`  | `ORGANISATION` | Company, school, outlet, etc.                     | Alphanumeric words with single spaces, 1–50 chars. Multiple spaces not allowed.<br/><br/>✅ **Valid:**<br/>- `o/Meta`<br/>- `o/New York Times` (single spaces only)<br/><br/>❌ **Invalid:**<br/>- <code>o/New&nbsp;&nbsp;York Times</code> (multiple space not allowed)<br/>- `o/Ben & Jerry` (special characters like `&` not allowed)<br/>- `o/ACME-Asia` (hyphen not allowed)<br/>- `o/` (empty)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| `r/`  | `ROLE`         | Job title or position (e.g., Reporter, Lawyer).   | Alphanumeric words with single spaces, 1–50 chars. Multiple spaces not allowed.<br/><br/>✅ **Valid:**<br/>- `r/Reporter`<br/>- `r/Data Analyst` (single spaces only)<br/><br/>❌ **Invalid:**<br/>- <code>r/Senior&nbsp;&nbsp;Reporter</code> (multiple space not allowed)<br/>- `r/Lead-editor` (special characters like `-` not allowed)<br/>- `r/` (empty)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `e/`  | `EMAIL`        | Email address used for contacting the person.     | **Format:** <code>local-part@domain</code><br/><br/><strong>Local-part (before @):</strong><br/>- only letters/digits allowed.<br/>- May use <code>+</code> <code>_</code> <code>.</code> <code>-</code> <em>between</em> letters/digits.<br/>- <strong>Cannot start or end</strong> with a special character.<br/>- <strong>No empty</strong> local-part.<br/><br/><strong>Domain (after @):</strong><br/>- One or more labels separated by dots (e.g., <code>sub.example.com</code>).<br/>- Each label can only contain letters/digits; optional internal hyphens (e.g., <code>news-room</code>).<br/>- A label <strong>cannot start or end</strong> with <code>-</code>.<br/>- <strong>No underscores</strong> in the domain.<br/>- The last part after the final dot (the “ending,” called the top-level domain or TLD, e.g., <code>.com</code>, <code>.sg</code>) must be <strong>at least 2 letters long.</strong><br/><br/><strong>✅ Valid</strong><br/>- <code>e/alice@example.com</code><br/>- <code>e/a.b-c+d@sub.example.co</code><br/>- <code>e/a_b@newsroom.io</code> (underscore allowed in local-part)<br/><br/><strong>❌ Invalid</strong><br/>- <code>e/&#95;alice@example.com</code> (local starts with <code>&#95;</code>)<br/>- <code>e/alice-@example.com</code> (local ends with <code>-</code>)<br/>- <code>e/alice@exa_mple.com</code> (underscore in domain)<br/>- <code>e/alice@example.c</code> (TLD only 1 letter)<br/>- <code>e/alice@-example.com</code> (domain label starts with <code>-</code>) |
| `p/`  | `PHONE`        | Phone number of a person.                         | **Digits only**, minimum **3** digits.<br/><br/>✅ **Valid:**<br/>- `p/123`<br/>- `p/98765432`<br/><br/>❌ **Invalid:**<br/>- `p/12` (too short, less than 3 digits)<br/>- `p/+651234567` (special characters like `+` not allowed)<br/>- `p/123-4567` (hyphen)<br/>- `p/(123)` (brackets)<br/>- `p/12 34` (extra space between digits)   |
| `c/`  | `CATEGORY`     | Tag for grouping (e.g., `emergency`, `election`). | **Alphanumeric only**, **no spaces**, **less than or equal to 20 chars**.<br/><br/>✅ **Valid:**<br/>- `c/emergency`<br/>- `c/singapore2025`<br/><br/>❌ **Invalid:**<br/>- `c/urgent-news` (hyphen)<br/>- `c/urgent news` (space)<br/>- `c/veryveryverylongcategory` (24 characters)<br/>- `c/` (empty)  |


  </tab>
  <tab header="📅 **Interview**">
  <h3>Interview Parameters</h3>

| Symbol | Parameter | Description                                        | Constraints                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
|:-----:|:----------|:---------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `h/`  | `HEADER`  | Short title for the interview (topic/company/etc.).| Non-empty string.                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| `d/`  | `DATE`    | Interview date.                                     | `yyyy-MM-dd` (4-digit year, 2-digit month/day). <br/><br/>✅ **Valid** (if no other interview timing clash with time):<br/>- `d/2025-10-15` (15 Oct 2025 is valid)<br/>- `d/1999-02-28`<br/><br/>❌ **Invalid:**<br/>- `d/2025/10/15` (slashes)<br/>- `d/2025-2-05` (missing leading zero for month)<br/>- `d/2025-13-01` (month 13 does not exist)<br/>- `d/2025-02-30` (invalid day, 30th Feb does not exist)<br/>- `d/25-10-15` (2-digit year, missing two leading zeros) |
| `t/`  | `TIME`    | Interview start time (24-hour).                     | `HH:mm` (00 to 23 for hours, 00 to 59 for minutes).<br/><br/>✅ **Valid** (if no other interview timing clash with date):<br/>- `t/00:00`<br/>- `t/09:05`<br/>- `t/23:59`<br/><br/>❌ **Invalid:**<br/>- `t/24:00` (hour out of range)<br/>- `t/12:60` (minute out of range)<br/>- `t/9:5` (missing leading zeros)<br/>- `t/12.30` (dot instead of colon)<br/>- `t/12:3` (single-digit minutes)                                                                              |
| `l/`  | `LOCATION`| Where it will happen (address, link, or note).      | Non-empty string.                                                                                                                                                                                                                                                                                                                                                                                                                                                          |


  </tab>

</tabs>

</details>

--------------------------------------------------------------------------------------------------------------------

## Features

<details class="caution" open>
  <summary>⚠️ CAUTION: Be careful when copy/pasting while using PDF!</summary>
  <div class="caution-body">
    If you are using a PDF version of this document, be careful when copying and pasting commands
    that span multiple lines — space characters surrounding line-breaks may be omitted when pasted
    into the application.
  </div>
</details>

### Viewing help : `help`
Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

> **Format**
> ```
> help
> ```
<br/><br/>

### Clearing all entries : `clear`

Clears all entries from the contact book.

**:warning: Caution:** This action is destructive and cannot be undone.

> **Format**
> ```
> clear
> ```
<br/><br/>

### Exiting the program : `exit`

Exits the program.

> **Format**
> ```
> exit
> ```
<br/><br/>

### Saving the data

PressPal data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.
<br/><br/>

### Adding a person : `add`

Adds a person to the contact book.
* You must provide at least one of `PHONE` and `EMAIL`.
* `PHONE` and `EMAIL` do not have to be unique. Different people can share the same contact details.

> **Format**
> ```
> add n/NAME p/PHONE_NUMBER e/EMAIL o/ORGANISATION r/ROLE [c/CATEGORY]
> ```

<div markdown="span" class="alert alert-primary">

:bulb: **Tip**:
A person can have any number of categories (including 0).
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com o/NUS r/Student c/friends c/owesMoney`
* `add n/Betsy Crowe p/12345678 e/betsycrowe@example.com o/Reuters r/Reporter`
<br/><br/>

### Deleting a person : `delete`

Deletes the specified person from the contact book.

> **Format**
> ```
> delete i/PERSON_INDEX
> ```

* Deletes the person at the specified `PERSON_INDEX`.

Examples:
* `list` followed by `delete i/2` deletes the 2nd person in the contact book.
* `find Betsy` followed by `delete i/1` deletes the 1st person in the results of the `find` command.
<br/><br/>

### Editing a person : `edit`

Edits an existing person in the contact book.

> **Format**
> ```
> edit i/PERSON_INDEX [n/NAME] [p/PHONE] [e/EMAIL] [o/ORGANISATION] [r/ROLE]
> ```

* Edits the person at the specified `PERSON_INDEX`.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit i/1 p/91234567 e/johndoe@example.com` Edits the phone number and email of the 1st person to be `91234567` and `johndoe@example.com` respectively.
<br/><br/>

### Listing all persons : `list`

Shows a list of all persons in the contact book.

> **Format**
> ```
> list
> ```
<br/><br/>

### Locating persons by name, organisation, role, or categories : `find`

Finds person(s) whose name, organisation, role or categories matches exactly with at least one of the given keywords.

> **Format** 
> ```
> find KEYWORD [MORE_KEYWORDS]
> ```

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name, organisation, role and categories are searched. Other fields such as phone or email are not included.
* The entered `KEYWORD` must match exactly with the intended search e.g. `Han` will not match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `find John` returns `john` and `John Doe`.
* `find bernice charlotte` returns `Bernice Yu`, `Charlotte Oliveiro`.<br>
  ![result for 'find bernice charlotte'](images/findBerniceCharlotteResult.png)
* `find NUS colleagues` Returns all persons whose details match the keyword of `NUS` or `colleagues`.
<br/><br/>

### Adding an interview to a contact : `addInterview`

Adds an interview to a contact in the contact book.

> **Format**
> ```
> addInterview i/PERSON_INDEX h/HEADER d/DATE t/TIME l/LOCATION
> ```

* Adds an interview to the contact at the specified `PERSON_INDEX`.
* When adding an interview, the `DATE` can be in the past.
* Each contact cannot have more than one interview at the same time. However, different contacts can have interviews at the same time.
* Different interviews can take place at the same `LOCATION`.
* Different interviews can have the same `HEADER`.

Examples:
* `addInterview i/1 h/Interview with ABC Corp d/2024-10-10 t/14:00 l/123, Business St, #02-25` adds an interview with header `Interview with ABC Corp`, date `2024-10-10`, time `14:00` and location `123, Business St, #02-25` to the 1st contact in the contact book.
<br/><br/>

### Deleting an interview from a contact : `deleteInterview`

Deletes an interview from a contact in the contact book.

> **Format** 
> ```
> deleteInterview i/PERSON_INDEX v/INTERVIEW_INDEX
> ```

* Deletes the interview at the specified `INTERVIEW_INDEX` from the contact at the specified `PERSON_INDEX`.

Examples:
* `deleteInterview i/1 v/2` Deletes the 2nd interview from the 1st contact in the contact book.
<br/><br/>

### Listing all interviews of a contact : `listInterview`

Lists all interviews of a contact in the contact book.

> **Format** 
> ```
> listInterview i/PERSON_INDEX
> ```

* Lists all interviews of the contact at the specified `PERSON_INDEX`.
* If the contact has no interviews, a message indicating so will be shown instead.

![empty interview](images/emptyInterviewExample.png)
![filled interview](images/filledInterviewExample.png)

<br/><br/>

### Display the upcoming interview : `nextInterview`

Displays the next scheduled interview that occurs at or after the current date and time, excluding any interviews already in the past.
* If the next scheduled interview occurs for more than one contact, they will all be displayed.

> **Format**
> ```
> nextInterview
> ```

Examples:
* `nextInterview` Displays the most upcoming scheduled interview for example, "[Meta Interview] on 15 Oct 2050 2:30PM at Meta HQ."
<br/><br/>

### Add category(s) to a person : `addCat`

Add category(s) to a person identified by the index number used in the displayed person list.

> **Format** 
> ```
> addCat i/PERSON_INDEX c/CATEGORY
> ```

* If a category is already added to a person, any attempt to add that category again to the person will be ignored.
  * E.g If `friend` is already a category added to person at index 1, running `addCat i/1 c/friend` will throw an error message. In a case with multiple categories (e.g `addCat i/1 c/friend c/work`), no error message is thrown but only `work` is added to person at index 1.
* At least one category must be specified, but you may choose to add more categories by repeating the use of `c/`.

Examples:
* `addCat i/1 c/emergency` Adds the category `emergency` to the person with index 1.
* `addCat i/2 c/emergency c/singapore` Adds the categories `emergency` and `singapore` to the person with index 2.
<br/><br/>

### Delete category(s) from a person : `deleteCat`

Delete category(s) from a person identified by the index number used in the displayed person list.

> **Format** 
> ```
> deleteCat i/PERSON_INDEX [c/CATEGORY]...
> ```

Examples:
* `deleteCat i/1 c/emergency` Deletes the category `emergency` from the person with index 1.
* `deleteCat i/2 c/emergency c/singapore` Deletes the categories `emergency` and `singapore` from the person with index 2.
<br/><br/>

--------------------------------------------------------------------------------------------------------------------

## Frequently Asked Questions (FAQ)

<h3 id="faq-general-qa" class="visually-hidden-anchor">General Q&A</h3>
<h3 id="faq-common-problems" class="visually-hidden-anchor">Common Problems and Fixes</h3>
<h3 id="faq-troubleshooting" class="visually-hidden-anchor">Troubleshooting</h3>
<h3 id="faq-feature-rationale" class="visually-hidden-anchor">Feature Rationale</h3>

<tabs>

  <tab header="🧠 General Q&A" active>

**Q:** <u>How do I move my data to another computer?</u>  
**A:** Install the app on the new computer, then replace its empty data file with the one from your old PressPal home folder.

**Q:** <u>Do I need to save my work?</u>  
**A:** Nope. Changes save automatically after each command.

**Q:** <u>How do I update the app?</u>  
**A:** Download the latest `.jar` from [here](https://github.com/AY2526S1-CS2103T-W08-1/tp/releases/latest) and replace your old one. Your data file stays intact.

**Q:** <u>Why didn’t a command work?</u>  
**A:** Check the format and parameter symbols (like `n/`, `e/`, `o/`). See the “Input Parameters” section/tabs above for the exact rules.

**Q:** <u>Why can't I see the full line of text when it gets too long?</u>  
**A:** The text extends beyond the visible area. Scroll horizontally (drag the scroll bar to the right) to view the rest of the line.

**Q:** <u>Can I use PressPal offline?</u>  
**A:** Yes. Everything runs locally; no internet is needed after download.

**Q:** <u>Is my data private?</u>  
**A:** Your data stays on your computer. We don’t sync it anywhere.
</tab>

  <tab header="🛠️ Common Problems & Fixes">

1. **App opens off-screen after unplugging a monitor**  
   – **Why:** The app remembers the last screen position, even if that screen isn’t there anymore.  
   – **Fix:** Delete `preferences.json`, then reopen the app.

2. **Help window won’t reappear after minimizing**  
   – **Why:** The original Help window is still minimized.  
   – **Fix:** Restore the minimized window manually.
   </tab>

  <tab header="🧩 Troubleshooting">

**Q:** <u>I found a bug, how do I report it?</u>  
**A:** Tell any developer, their emails are listed [here](AboutUs.md).

**Q:** <u>I have an idea, where do I suggest it?</u>  
**A:** Share it with any developer, contacts are found [here](AboutUs.md).
</tab>

  <tab header="💡 Feature Rationale">

**Q:** <u>Why does `addInterview` allow past dates?</u>  
**A:** So you can record past interviews for reference.

**Q:** <u>Why can I schedule two interviews at the same time?</u>  
**A:** Different people may attend the same session, so overlaps across contacts are allowed.

**Q:** <u>Why does `nextInterview` show only one person even if a few interviews share the same time?</u>  
**A:** To keep things fast and clear. `nextInterview` gives you one “next up” so you can act right away.  
If several interviews have the same time, we show the **first one in your current list**. The others are still there and you view them as usual (e.g., `listInterview i/…`).

**Q:** <u>Why are non-alphanumeric characters (e.g special symbols) disallowed for name, organisation, role & categories? For instance, I would not be able to save 'Ben & Jerry's. </u>  
**A:** Currently, we do not accept non-alphanumerical symbols for a more streamlined search process. We understand that this may not fully capture real world's dynamic and aim to support greater range of inputs in the future. For now, you could try workarounds. For instance, using 'Ben and Jerry' as a substitute.

</tab>

</tabs>

--------------------------------------------------------------------------------------------------------------------

## Future Iteration Plans

<panel header="🚧 Roadmap (what’s coming next)" type="warning" expanded>

<div class="roadmap-grid">

  <div class="roadmap-card">
    <h4>🗄️ Archive contacts & interviews <span class="chip planned">Planned</span></h4>
    <div class="roadmap-meta">
      <span>Goal: keep the main list clutter-free</span>
    </div>
    <strong>Why it matters</strong>
    <ul class="why">
      <li>Old sources pile up and hide the ones you’re working with now.<br/><br/></li>
    </ul>
    <strong>You’ll be able to:</strong>
    <ul class="you-can">
      <li>Move contacts/interviews to an <em>Archive</em> with one command.</li>
      <li>Search and restore archived items anytime.</li>
    </ul>
    <div class="roadmap-actions">
      <a class="btn-ghost" href="#locating-persons-by-name-organisation-role-or-categories-find">Related: search (`find`)</a>
    </div>
  </div>

  <div class="roadmap-card">
    <h4>📵 Enforce unique phone & email <span class="chip proposed">Proposed</span></h4>
    <div class="roadmap-meta">
      <span>Goal: reduce accidental duplicates</span>
    </div>
    <strong>Why it matters</strong>
    <ul class="why">
      <li>Duplicate entries waste time and cause mistakes during outreach.<br/><br/></li>
    </ul>
    <strong>You’ll be able to:</strong>
    <ul class="you-can">
      <li>Get a clear prompt when a number/email already exists.</li>
      <li>Choose to open the existing contact or continue (if intentional).</li>
    </ul>
    <div class="roadmap-actions">
      <a class="btn-ghost" href="#adding-a-person-add">Related: add (`add`)</a>
      <a class="btn-ghost" href="#editing-a-person-edit">Related: edit (`edit`)</a>
    </div>
  </div>

  <div class="roadmap-card">
    <h4>📝 Interview notes <span class="chip planned">Planned</span></h4>
    <div class="roadmap-meta">
      <span>Goal: keep prep & takeaways beside the interview</span>
    </div>
    <strong>Why it matters</strong>
    <ul class="why">
      <li>No more jumping between apps to find context and follow-ups.<br/><br/></li>
    </ul>
    <strong>You’ll be able to:</strong>
    <ul class="you-can">
      <li>Add bullet points, links, and quick tags to each interview.</li>
      <li>See notes when listing or opening interviews.</li>
    </ul>
    <div class="roadmap-actions">
      <a class="btn-ghost" href="#listing-all-interviews-of-a-contact-listinterview">Related: listInterview</a>
      <a class="btn-ghost" href="#adding-an-interview-to-a-contact-addinterview">Related: addInterview</a>
    </div>
  </div>

</div>

</panel>

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format, Examples
--------|------------------
**add** | `add n/NAME p/PHONE e/EMAIL o/ORGANISATION r/ROLE [c/CATEGORY]…​` <br> e.g., `add n/John Doe p/98765432 e/johnd@example.com o/NUS r/Student c/friends c/owesMoney`
**clear** | `clear`
**delete** | `delete i/PERSON_INDEX`<br> e.g., `delete i/3`
**edit** | `edit i/PERSON_INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [o/ORGANISATION] [r/ROLE]​`<br> e.g.,`edit i/2 n/James Lee e/jameslee@example.com`
**find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James NUS Student`
**list** | `list`
**addInterview** | `addInterview i/PERSON_INDEX h/HEADER d/DATE t/TIME l/LOCATION` <br> e.g., `addInterview i/1 h/Interview with ABC Corp d/2024-10-10 t/14:00 l/123, Business St, #02-25`
**deleteInterview** | `deleteInterview i/PERSON_INDEX v/INTERVIEW_INDEX` <br> e.g., `deleteInterview i/1 v/2`
**listInterview** | `listInterview i/PERSON_INDEX` <br> e.g., `listInterview i/1`
**addCat** | `addCat i/PERSON_INDEX c/CATEGORY [c/CATEGORY]`<br>e.g., `addCat i/1 c/emergency`
**deleteCat** | `deleteCat i/PERSON_INDEX c/CATEGORY [c/CATEGORY]`<br>e.g., `deleteCat i/1 c/emergency`
**nextInterview** | `nextInterview`
**exit** | `exit`
**help** | `help`
