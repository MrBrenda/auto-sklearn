# Paper Reproduction

Skill for reproducing results from AutoML research papers, especially the auto-sklearn papers.

## When to Use
When the user wants to reproduce published results, validate claims from papers, or build upon existing research.

## Instructions

1. **Paper Analysis**: Help understand the experimental setup.
   - Identify the key claims and experiments in the paper
   - Extract hyperparameter configurations, datasets used, and evaluation protocols
   - Note any specific software versions or environment requirements
   - List the tables/figures that need to be reproduced

2. **Environment Setup**: Recreate the experimental environment.
   - Match the auto-sklearn version used in the paper
   - Install specific dependency versions from the paper's requirements
   - Use Docker if needed for exact environment reproduction
   - Set the same random seeds mentioned in the paper

3. **Dataset Acquisition**: Obtain the exact datasets used.
   - Download datasets from OpenML by task ID when available
   - For the original auto-sklearn paper: use the OpenML-100 or AutoML benchmark datasets
   - Verify dataset preprocessing matches the paper's description
   - Check train/test splits match the paper's protocol

4. **Experiment Execution**: Run the experiments faithfully.
   - Match time budgets, memory limits, and other resource constraints
   - Use the same evaluation protocol (CV folds, test splits)
   - Run multiple repetitions as specified in the paper
   - Log all intermediate results for debugging

5. **Results Comparison**: Compare reproduced vs. published results.
   - Create side-by-side comparison tables
   - Calculate deviation from published results
   - Document any discrepancies and likely causes
   - Note hardware differences that may affect runtime comparisons

6. **Key Auto-sklearn Papers**:
   - "Efficient and Robust Automated Machine Learning" (NeurIPS 2015)
   - "Auto-sklearn 2.0" (JMLR 2022)
   - Reference the metalearning metadata in `autosklearn/metalearning/files/`
