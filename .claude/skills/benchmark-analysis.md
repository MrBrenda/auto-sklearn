# Benchmark Analysis

Skill for running and analyzing benchmarks comparing auto-sklearn against baselines and other AutoML systems.

## When to Use
When the user wants to benchmark auto-sklearn, compare algorithms, or evaluate performance across multiple datasets.

## Instructions

1. **Benchmark Design**: Help design a rigorous benchmark.
   - Select appropriate datasets (OpenML, UCI, or custom)
   - Define baseline methods (default sklearn, random search, grid search)
   - Choose evaluation metrics aligned with the research question
   - Plan cross-validation strategy (k-fold, repeated k-fold, nested CV)

2. **Multi-Dataset Evaluation**: Run experiments across datasets.
   ```python
   import openml
   from autosklearn.classification import AutoSklearnClassifier
   from sklearn.model_selection import cross_val_score

   # Example: benchmark on OpenML suite
   results = {}
   for dataset_id in dataset_ids:
       dataset = openml.datasets.get_dataset(dataset_id)
       X, y, _, _ = dataset.get_data(target=dataset.default_target_attribute)

       automl = AutoSklearnClassifier(time_left_for_this_task=3600)
       scores = cross_val_score(automl, X, y, cv=5, scoring='accuracy')
       results[dataset_id] = scores
   ```

3. **Statistical Testing**: Apply proper statistical comparison methods.
   - Wilcoxon signed-rank test for pairwise comparisons
   - Friedman test with Nemenyi post-hoc for multiple classifiers
   - Report p-values and effect sizes
   - Generate critical difference diagrams

4. **Results Visualization**: Create publication-quality plots.
   - Box plots of performance distributions
   - Convergence curves (performance over time)
   - Scatter plots for pairwise comparison
   - Heatmaps for multi-dataset/multi-method comparisons
   - Use matplotlib with consistent styling for papers

5. **Results Reporting**: Generate structured reports.
   - Summary tables with mean ± std performance
   - Win/tie/loss counts across datasets
   - Ranking tables
   - Export results to LaTeX tables for papers
